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

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Video extends IstributeObject {

    protected String id;
    protected JsonObject data;
    protected String title = null;
    protected boolean titleSet = false;

    public Video(Istribute istribute, String id) {
        super(istribute);
        this.id = id;
        this.data = null;
    }

    protected String getUrlPath() {
        return String.format("/video/%s/%s", istribute.getAppId(), id);
    }

    protected JsonObject getData() throws IstributeErrorException, InvalidResponseException, IOException {
        if (data == null) {
            data = istribute.get(String.format("%s.json", getUrlPath()));
        }
        return data;
    }

    protected JsonArray getSourcesData() {
        JsonArray sourcesData = null;
        try {
            sourcesData = getData().getJsonArray("sources");
        } catch (IstributeErrorException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace(); // TODO.
        } catch (IOException e) {
            e.printStackTrace(); // TODO.
        }
        return sourcesData;
    }

    protected String getPlayerUrl() {
        return this.getPlayerUrl(false);
    }

    protected String getPlayerUrl(boolean authenticated) {
        String path = getUrlPath();
        if (authenticated) {
            try {
                path = istribute.sign(path);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace(); // TODO: Do something meaningful with the exception.
            }
        }
        return String.format("%s%s", istribute.getServerUrl(), path);
    }

    public Collection<VideoSource> getSources() {
        Collection<VideoSource> sources = new ArrayList<VideoSource>();
        for (int i = 0; i < getSourcesData().size(); i++) {
            JsonObject data = getSourcesData().getJsonObject(i);
            sources.add(new VideoSource(data));
        }
        return sources;
    }

    public String getId() {
        return id;
    }

    public String getTitle() throws IOException, InvalidResponseException, IstributeErrorException {
        if (titleSet) {
            return title;
        } else {
            return getData().getString("title");
        }
    }

    public void setTitle(String title) {
        this.title = title;
        this.titleSet = true;
    }

    public String getState() throws IOException, InvalidResponseException, IstributeErrorException {
        return getData().getString("state");
    }

    public String getPreviewImage() throws IOException, InvalidResponseException, IstributeErrorException {
        return getData().getString("previewImage");
    }

    public float getAspect() throws IOException, InvalidResponseException, IstributeErrorException {
        return Float.parseFloat(getData().getJsonNumber("aspect").toString());
    }

    public JsonArray getDownloadUrls() throws IOException, InvalidResponseException, IstributeErrorException {
        return getData().getJsonArray("downloadUrls");
    }

    public String getThumbnailStatus() throws IOException, InvalidResponseException, IstributeErrorException {
        return getData().getString("thumbnailStatus");
    }

    public String getIntervalThumbnailVtt() throws IOException, InvalidResponseException, IstributeErrorException {
        return getData().getString("intervalThumbnailVtt");
    }

    public int getTimestamp() throws IOException, InvalidResponseException, IstributeErrorException {
        return getData().getInt("timestamp");
    }

    public void save() throws IstributeErrorException, InvalidResponseException, IOException {
        if (titleSet) {
            Map<String,String> postPayload = new HashMap<String,String>();
            postPayload.put("video_title", title);
            String uri;
            try {
                uri = "/v1/video/edit/?videoId=" + URLEncoder.encode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 is supposed to work", e);
            }
            JsonObject result = istribute.post(uri, postPayload);
            System.out.println(result.toString());
        }
    }
}
