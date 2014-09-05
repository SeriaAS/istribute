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

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class VideoTest {

    public static final String APP_ID = "tZgTUJT";
    public static final String APP_KEY = "K2xv3FCYp2tzpmAWVY4ur4rPrxmh0FcA";
    public static final String SERVER_URL = "https://joneirikdev-apiistributecom.webhosting.seria.net";

    public static final String VIDEO_ID = "urgu6LY8N1zz1ro";

    public void testGetUrlPath() throws Exception {

    }

   public void testGetData() throws Exception {

    }

    public void testGetSourcesData() throws Exception {

    }

    public void testGetPlayerUrl() throws Exception {

    }

    public void testGetPlayerUrl1() throws Exception {

    }

    @Test
    public void testGetSources() throws Exception {
        Istribute istribute = new Istribute(APP_ID, APP_KEY, SERVER_URL);
        Video video = new Video(istribute, VIDEO_ID);
        Collection<VideoSource> videoSources = video.getSources();
        for (VideoSource videoSource : videoSources) {
            System.out.println(videoSource.getName());
            System.out.println(videoSource.getLabel());
        }
    }

    public void testGetId() throws Exception {

    }

    public void testGetTitle() throws Exception {

    }

    public void testGetState() throws Exception {

    }

    public void testGetPreviewImage() throws Exception {

    }

    public void testGetAspect() throws Exception {

    }

    public void testGetDownloadUrls() throws Exception {

    }

    public void testGetThumbnailStatus() throws Exception {

    }

    public void testGetIntervalThumbnailVtt() throws Exception {

    }

    public void testGetTimestamp() throws Exception {

    }
}