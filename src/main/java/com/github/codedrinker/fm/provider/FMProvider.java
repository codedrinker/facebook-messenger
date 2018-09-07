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
import com.github.codedrinker.fm.entity.*;
import com.github.codedrinker.fm.exception.AccessTokenUndefinedException;
import com.github.codedrinker.fm.net.HttpClientHelper;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

    /**
     * Messenger 配置 API 改为 messenger_profile
     *
     * @param message
     * @see #sendProfileSetting(FMProfileSettingMessage)
     */
    @Deprecated
    public static FMResult sendSetting(FMSettingMessage message) {
        logger.info("FMProvider => sendSetting message= {} ", JSON.toJSONString(message));
        String URL = String.format("https://graph.facebook.com/v2.6/me/thread_settings?access_token=%s", getAccessToken());
        String string = JSON.toJSONString(message);
        FMResult fmpResult = post(URL, string);
        FMClient.getInstance().getFmResultAspect().handle(fmpResult);
        return fmpResult;
    }

    /**
     * 配置Messenger Profile，例如 Persistent Menu，Get Started Button，Welcome Page ...
     * 该API 调用 限制频率不超过 每10分钟 10次
     */
    public static FMResult sendProfileSetting(FMProfileSettingMessage message) {
        logger.info("FMProvider => sendProfileSetting message= {} ", JSON.toJSONString(message));
        String URL = String.format("https://graph.facebook.com/v2.6/me/messenger_profile?access_token=%s", getAccessToken());
        String string = JSON.toJSONString(message);
        FMResult fmpResult = post(URL, string);
        FMClient.getInstance().getFmResultAspect().handle(fmpResult);
        return fmpResult;
    }

    public static FMUser getUserProfile(String id) {
        String URL = String.format("https://graph.facebook.com/v2.6/%s?access_token=%s", id, getAccessToken());

        try {
            String result = HttpClientHelper.getSync(URL);
            FMUser user = JSON.parseObject(result, FMUser.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static FMResult post(String url, String string) {
        OkHttpClient client = HttpClientHelper.createClient().build();
        try {
            String result = HttpClientHelper.postSync(client, url, string);
            logger.info("postSync, result -> {}", result);
            FMResult fmpResult = JSON.parseObject(result, FMResult.class);
            logger.info("post url -> {}, string -> {}, result -> {}", url, string, result);
            return fmpResult;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Request Facebook Message API error with url : {}", url, e);
        }
        return null;
    }
}
