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
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.io.IOException;

/**
 * 担负与 Messenger Platform API 交互的责任
 */
@Slf4j
public class FMProvider {

    private static String getAccessToken() {
        String accessToken;
        if (FMClient.getInstance().getAccessToken() == null) {
            throw new AccessTokenUndefinedException();
        } else {
            accessToken = FMClient.getInstance().getAccessToken();
        }
        log.info("FMProvider => getAccessToken {}", accessToken);
        return accessToken;
    }

    /**
     * 发送消息
     *
     * @param message FMReplyMessage
     * @return FMResult
     */
    public static FMResult sendMessage(FMReplyMessage message) {
        log.info("FMProvider => sendMessage message: {}", JSON.toJSONString(message));

        String URL = String.format("https://graph.facebook.com/v2.6/me/messages?access_token=%s", getAccessToken());
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
        log.info("FMProvider => sendProfileSetting message= {} ", JSON.toJSONString(message));

        String URL = String.format("https://graph.facebook.com/v2.6/me/messenger_profile?access_token=%s", getAccessToken());
        String string = JSON.toJSONString(message);
        FMResult fmpResult = post(URL, string);
        FMClient.getInstance().getFmResultAspect().handle(fmpResult);
        return fmpResult;
    }

    /**
     * 上传附件
     *
     * @param uploadMessage {@link FMUploadMessage}
     * @return FMAttachmentUploadResult
     */
    public static FMAttachmentUploadResult uploadAttachment(FMUploadMessage uploadMessage) {
        String accessToken = getAccessToken();
        String url = String.format("https://graph.facebook.com/v2.6/me/message_attachments?access_token=%s", accessToken);
        String string = JSON.toJSONString(uploadMessage);
        try {
            String result = HttpClientHelper.postSync(url, string);
            FMAttachmentUploadResult res = JSON.parseObject(result, FMAttachmentUploadResult.class);
            log.error("FMPProvider uploadAttachment => {}", JSON.toJSONString(res));
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户Profile 信息
     *
     * @param id 用户的Messenger Id
     * @return RawFMUser
     */
    public static RawFMUser getUserProfile(String id) {
        String URL = String.format("https://graph.facebook.com/v2.6/%s?access_token=%s", id, getAccessToken());

        try {
            String result = HttpClientHelper.getSync(URL);
            RawFMUser user = JSON.parseObject(result, RawFMUser.class);
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
            log.info("postSync, result -> {}", result);
            FMResult fmpResult = JSON.parseObject(result, FMResult.class);
            log.info("post url -> {}, string -> {}, result -> {}", url, string, result);
            return fmpResult;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Request Facebook Message API error with url : {}", url, e);
        }
        return null;
    }
}
