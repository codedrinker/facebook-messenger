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
package com.github.codedrinker.fm.entity;

import lombok.Data;

/**
 * 与 Messenger Platform API 交互的响应数据
 * <p>
 * 对于使用 recipient.user_ref 或 recipient.phone_number 指定消息收件人的消息发送请求,
 * 发送 API 的响应中不再包含 recipient_id.
 */
@Data
public class FMResult {
    private Error error;
    /**
     * 用户的唯一编号
     */
    private String recipient_id;
    /**
     * 消息的唯一编号
     */
    private String message_id;
    private String result;

    /**
     * 发送API请求失败时的错误代码见：
     * <p>
     * https://developers.facebook.com/docs/messenger-platform/reference/send-api/error-codes
     */
    @Data
    public static class Error {
        private String message;
        private String type;
        private String code;
        private String error_subcode;
        private String fbtrace_id;
    }
}
