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

import javax.json.JsonObject;
import java.io.*;
import java.net.URLEncoder;

public class Istribute extends Http {

    public Istribute(String appId, String appKey, String serverUrl) {
        super(appId, appKey, serverUrl);
    }

    public Istribute getAnonymousIstribute() {
        return new Istribute(getAppId(), null, getServerUrl());
    }

    public VideoList getVideoList() {
        return new VideoList(this);
    }

    public Video getVideo(String id) {
        return new Video(this, id);
    }

    public Video uploadVideo(String filename) throws IOException, IstributeErrorException, InvalidResponseException {
        JsonObject data = put(String.format("/v1/video/put/?md5=%s", URLEncoder.encode(md5File(filename), "UTF-8")), filename);
        if (data == null || data.getString("videoId") == null) {
            return null;
        }
        String id = data.getString("videoId");
        if (id != null) {
            return new Video(this, id);
        } else {
            return null;
        }
    }

    private static String md5File(String filename) {
        String result = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(filename));
            result = DigestUtils.md5Hex(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // TODO: Do something meaningful with the exception (or re-throw it).
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Do something meaningful with the exception (or re-throw it).
        }
        return result;
    }
}
