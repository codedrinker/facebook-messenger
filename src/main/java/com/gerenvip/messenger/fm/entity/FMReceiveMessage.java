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
package com.gerenvip.messenger.fm.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

@Data
public class FMReceiveMessage {

    private String object;

    private List<Entry> entry;

    @Data
    public static class Entry {

        private String id;
        private long time;
        private String uid;
        private List<Change> changes;
        private List<String> changed_fields;//change 只有 field 字段有内容
        private List<Messaging> messaging;
        /**
         * https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/standby
         * 备用 信息通道，不同于 messaging
         */
        private List<Messaging> standby;

        /**
         * 通过正常 messaging 字段获取消息。如果不存在，尝试通过备用通道获取
         */
        public List<Messaging> getMessaging() {
            if (messaging != null) {
                System.out.println("Entry => messaging =>" + JSON.toJSONString(messaging));
                return messaging;
            }
            System.out.println("Entry => standby =>" + JSON.toJSONString(standby));
            return standby;
        }
    }

    /**
     * 用于记录 绑定的 Facebook Page 或者 User，Application，Instagram 等等域 的一些配置字段的变化。<br/>
     * 只要在 开发者后台的 Webhook 配置中订阅 的字段 ，当这些字段发生变化时，才能收到该 数据。
     * <br/>
     * 例如:
     * <br/>
     * 订阅了 Facebook Page 的 email 字段，当有管理员修改了 Facebook page 上的 email 时， Messenger Bold 就会 收到该消息
     */
    @Data
    public static class Change {
        public String field;//变化的字段
        public String value;//变化的字段值

        public static Change emptyValue(String field) {
            Change change = new Change();
            change.field = field;
            return change;
        }
    }

    @Data
    public static class Messaging {

        private Member sender;
        private Member recipient;
        private long timestamp;
        private Message message;
        private Delivery delivery;
        private Read read;
        private PostBack postback;
        private Referral referral;

        @Data
        public static class Referral {
            private String ref;
            private String source;
            private String type;
        }

        @Data
        public static class PostBack {

            private String payload;
            private Referral referral;
        }

        @Data
        public static class Delivery {
            private List<String> mids;
            private long watermark;
            private long seq;
        }

        @Data
        public static class Read {
            private long watermark;
            private long seq;
        }

        @Data
        public static class Message {
            private Boolean is_echo;
            private String app_id;
            private String metadata;

            private String mid;
            private Integer seq;
            private String text;
            private QuickReply quick_reply;
            private List<Attachment> attachments;

            @Data
            public static class Attachment {

                private String title;//title
                private String type;//image、audio、video、file 或 location
                private Payload payload;//multimedia 或 location 负载

                @Data
                public static class Payload {

                    private String url;
                }
            }

            @Data
            public static class QuickReply {

                private String payload;
            }

        }

        @Data
        public static class Member {

            public String id;
        }
    }

}
