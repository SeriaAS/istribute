/*
 * Copyright (c) 2014, Seria AS
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.seria.istribute.sdk;

import no.seria.istribute.sdk.exception.InvalidResponseException;
import no.seria.istribute.sdk.exception.IstributeErrorException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.json.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Http {

    protected String serverUrl;
    protected String appId;
    protected String appKey;

    public Http(String appId, String appKey, String serverUrl) {
        this.appId = appId;
        this.appKey = appKey;
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    private JsonObject jsonResponseFilter(String data) throws InvalidResponseException, IstributeErrorException {
        JsonObject jsonData = parsePayload(data);
        if (jsonData == null) {
            throw new InvalidResponseException("Result from Istribute is not valid JSON");
        }
        return jsonData;
    }

    public String sign(String path) throws UnsupportedEncodingException {
        return this.sign(path, null);
    }

    public String sign(String path, Long expiry) throws UnsupportedEncodingException {
        String fragment;
        if (appKey == null) {
            return serverUrl + path;
        }
        // Add the appId GET parameter.
        if (path.contains("?")) {
            fragment = String.format("%s&", path);
        } else {
            fragment = String.format("%s?", path);
        }
        fragment = String.format("%sappId=%s", fragment, URLEncoder.encode(appId, "UTF-8"));

        // If expiry is not provided, expire the URL after 24 hours.
        if (expiry == null) {
            long unixTimestamp = System.currentTimeMillis() / 1000L;
            long computedExpiry = unixTimestamp + (3600 * 24);
            fragment = String.format("%s&signExpiry=%s", fragment, String.valueOf(computedExpiry));
        } else {
            fragment = String.format("%s&signExpiry=%s", fragment, expiry);
        }
        // Sign the path.
        String signature = null;
        signature = URLEncoder.encode(hmacDigest(path, appKey, "HmacSHA256"), "UTF-8"); // TODO: Verify logic: anonymous Istribute has a null appKey.

        // Construct full endpoint.
        return String.format("%s%s&signature=%s", serverUrl, fragment, signature);
    }

    private <T extends JsonStructure>T parsePayload(String payload) {
        T result;

        InputStream stream = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8));
        try (JsonReader jsonObjectReader = Json.createReader(stream)) {
            try {
                result = (T) jsonObjectReader.readObject();
            } catch (JsonException e) {
                stream = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8)); // stream.reset();
                try (JsonReader jsonArrayReader = Json.createReader(stream)) {
                    result = (T) jsonArrayReader.readArray();
                }
            }
        }
        return result;
    }

    // http://www.supermind.org/blog/1102/generating-hmac-md5-sha1-sha256-etc-in-java
    private static String hmacDigest(String msg, String keyString, String algorithm) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage()); // TODO: Do something meaningful with the exception (or re-throw).
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
        return digest;
    }

    // A generic method to execute any type of Http Request and constructs a response object.
    private static String executeRequest(HttpRequestBase httpRequestBase) throws IOException {
        String responseString = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequestBase)) {
                if (httpResponse != null) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        InputStream inputStream = httpEntity.getContent();

                        // Scanner iterates over tokens in the stream, and in this case we separate tokens using
                        // “beginning of the input boundary” (A) thus giving us only one token for the entire contents
                        // of the stream.
                        responseString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
                        inputStream.close();
                    }
                }
            }
        }
        return responseString;
    }

    public <T extends JsonStructure>T get(String path) throws IOException, InvalidResponseException, IstributeErrorException {
        String signedUrl = sign(path);
        HttpGet httpGet = new HttpGet(signedUrl);

        String responseString = executeRequest(httpGet);
        return parsePayload(responseString);
    }

    public <T extends JsonStructure>T put(String path, String filename) throws IOException, InvalidResponseException, IstributeErrorException {
        String signedUrl = sign(path);
        HttpPut httpPut = new HttpPut(signedUrl);

        Path binaryPath = Paths.get(filename);
        byte[] binary = Files.readAllBytes(binaryPath);

        HttpEntity requestEntity = EntityBuilder.create()
                .setBinary(binary)
                .build();
        httpPut.setEntity(requestEntity);
        String responseString = executeRequest(httpPut);

        return parsePayload(responseString);
    }

    public <T extends JsonStructure>T post(String path, Map<String, String> fields) throws IOException, InvalidResponseException, IstributeErrorException {
        List<NameValuePair> postData = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // TODO: Review.
        String postChecksum = DigestUtils.md5Hex(String.valueOf(postData.hashCode()).getBytes());

        String endpoint;
        if (path.contains("?")) {
            endpoint = String.format("%s&postChecksum=%s", path, URLEncoder.encode(postChecksum, "UTF-8"));
        } else {
            endpoint = String.format("%s?postChecksum=%s", path, URLEncoder.encode(postChecksum, "UTF-8"));
        }

        String signedUrl = sign(endpoint);
        HttpPost httpPost = new HttpPost(signedUrl);

        httpPost.setEntity(new UrlEncodedFormEntity(postData));
        String responseString = executeRequest(httpPost);

        return parsePayload(responseString);
    }
}
