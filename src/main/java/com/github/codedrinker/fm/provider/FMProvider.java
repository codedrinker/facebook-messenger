/*
 * Copyright 2017 Chunlei Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.codedrinker.fm.provider;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.FMClient;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.entity.FMResult;
import com.github.codedrinker.fm.entity.FMSettingMessage;
import com.github.codedrinker.fm.entity.FMUser;
import com.github.codedrinker.fm.exception.AccessTokenUndefinedException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class FMProvider {
    final static Logger logger = LoggerFactory.getLogger(FMProvider.class);

    private static String getAccessToken() {
        String accessToken;
        if (FMClient.getInstance().getAccessToken() == null) {
            throw new AccessTokenUndefinedException();
        } else {
            accessToken = FMClient.getInstance().getAccessToken();
        }
        return accessToken;
    }

    public static FMResult sendMessage(FMReplyMessage message) {

        String URL = String.format("https://graph.facebook.com/v2.6/me/messages?access_token=%s", getAccessToken());
        String string = JSON.toJSONString(message);
        FMResult fmpResult = post(URL, string);
        FMClient.getInstance().getFmResultAspect().handle(fmpResult);
        return fmpResult;
    }

    public static FMResult sendSetting(FMSettingMessage message) {
        String URL = String.format("https://graph.facebook.com/v2.6/me/thread_settings?access_token=%s", getAccessToken());
        String string = JSON.toJSONString(message);
        FMResult fmpResult = post(URL, string);
        FMClient.getInstance().getFmResultAspect().handle(fmpResult);
        return fmpResult;
    }

    public static FMUser getUserProfile(String id) {
        String URL = String.format("https://graph.facebook.com/v2.6/%s?access_token=%s", id, getAccessToken());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        httpGet.getMethod();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            FMUser user = JSON.parseObject(result, FMUser.class);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    private static FMResult post(String url, String string) {
        HttpResponse res;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            StringEntity params = new StringEntity(string, StandardCharsets.UTF_8);
            params.setContentEncoding("UTF-8");
            params.setContentType("application/json");
            post.setEntity(params);
            res = httpClient.execute(post);
            String result = EntityUtils.toString(res.getEntity());
            FMResult fmpResult = JSON.parseObject(result, FMResult.class);
            return fmpResult;
        } catch (Exception e) {
            logger.error("Request Facebook Message API error with url : {}", url, e);
            return null;
        }
    }
}
