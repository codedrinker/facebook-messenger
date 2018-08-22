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

import java.util.List;

public class FMReceiveMessage {

    private String object;

    private List<Entry> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public static class Entry {

        private String id;
        private long time;
        private List<Messaging> messaging;
        /**
         * https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/standby
         * 备用 信息通道，不同于 messaging
         */
        private List<Messaging> standby;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        /**
         * 通过正常 messaging 字段获取消息。如果不存在，尝试通过备用通道获取
         */
        public List<Messaging> getMessaging() {
            if (messaging != null) {
                return messaging;
            }
            return standby;
        }

        public void setMessaging(List<Messaging> messaging) {
            this.messaging = messaging;
        }
    }

    public static class Messaging {

        private Member sender;
        private Member recipient;
        private long timestamp;
        private Message message;
        private Delivery delivery;
        private Read read;
        private PostBack postback;
        private Referral referral;


        public Member getSender() {
            return sender;
        }

        public void setSender(Member sender) {
            this.sender = sender;
        }

        public Member getRecipient() {
            return recipient;
        }

        public void setRecipient(Member recipient) {
            this.recipient = recipient;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public Delivery getDelivery() {
            return delivery;
        }

        public void setDelivery(Delivery delivery) {
            this.delivery = delivery;
        }

        public Read getRead() {
            return read;
        }

        public void setRead(Read read) {
            this.read = read;
        }

        public PostBack getPostback() {
            return postback;
        }

        public void setPostback(PostBack postback) {
            this.postback = postback;
        }

        public Referral getReferral() {
            return referral;
        }

        public void setReferral(Referral referral) {
            this.referral = referral;
        }

        public static class Referral {
            private String ref;
            private String source;
            private String type;

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class PostBack {

            private String payload;
            private Referral referral;

            public String getPayload() {
                return payload;
            }

            public void setPayload(String payload) {
                this.payload = payload;
            }

            public Referral getReferral() {
                return referral;
            }

            public void setReferral(Referral referral) {
                this.referral = referral;
            }
        }

        public static class Delivery {
            private List<String> mids;
            private long watermark;
            private long seq;

            public List<String> getMids() {
                return mids;
            }

            public void setMids(List<String> mids) {
                this.mids = mids;
            }

            public long getWatermark() {
                return watermark;
            }

            public void setWatermark(long watermark) {
                this.watermark = watermark;
            }

            public long getSeq() {
                return seq;
            }

            public void setSeq(long seq) {
                this.seq = seq;
            }
        }

        public static class Read {
            private long watermark;
            private long seq;

            public long getWatermark() {
                return watermark;
            }

            public void setWatermark(long watermark) {
                this.watermark = watermark;
            }

            public long getSeq() {
                return seq;
            }

            public void setSeq(long seq) {
                this.seq = seq;
            }
        }

        public static class Message {
            private Boolean is_echo;
            private String app_id;
            private String metadata;

            private String mid;
            private Integer seq;
            private String text;
            private QuickReply quick_reply;
            private List<Attachment> attachments;

            public Boolean getIs_echo() {
                return is_echo;
            }

            public void setIs_echo(Boolean is_echo) {
                this.is_echo = is_echo;
            }

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public String getMetadata() {
                return metadata;
            }

            public void setMetadata(String metadata) {
                this.metadata = metadata;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public Integer getSeq() {
                return seq;
            }

            public void setSeq(Integer seq) {
                this.seq = seq;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public QuickReply getQuick_reply() {
                return quick_reply;
            }

            public void setQuick_reply(QuickReply quick_reply) {
                this.quick_reply = quick_reply;
            }

            public List<Attachment> getAttachments() {
                return attachments;
            }

            public void setAttachments(List<Attachment> attachments) {
                this.attachments = attachments;
            }

            public static class Attachment {
                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Payload getPayload() {
                    return payload;
                }

                public void setPayload(Payload payload) {
                    this.payload = payload;
                }

                private String title;//title
                private String type;//image、audio、video、file 或 location
                private Payload payload;//multimedia 或 location 负载

                public static class Payload {
                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    private String url;
                }
            }

            public static class QuickReply {
                public String getPayload() {
                    return payload;
                }

                public void setPayload(String payload) {
                    this.payload = payload;
                }

                private String payload;
            }

        }

        public static class Member {
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String id;
        }
    }

}
