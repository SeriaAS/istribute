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

import java.util.LinkedList;

import static org.junit.Assert.*;

public class VideoListTest {

    public static final String APP_ID = "tZgTUJT";
    public static final String APP_KEY = "K2xv3FCYp2tzpmAWVY4ur4rPrxmh0FcA";
    public static final String SERVER_URL = "https://joneirikdev-apiistributecom.webhosting.seria.net";

    public void testGetVideos() throws Exception {

    }

    @Test
    public void testGetIds() throws Exception {
        Istribute istribute = new Istribute(APP_ID, APP_KEY, SERVER_URL);
        VideoList videoList = new VideoList(istribute);

        LinkedList<String> ids = videoList.getIds();
        for (String id : ids) {
            System.out.println(id);
        }
    }
}