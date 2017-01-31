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

import com.github.codedrinker.fm.exception.QuickReplyOutOfBoundException;

import java.util.List;

public class FMReplyMessage {
    private Member recipient;
    private Message message;
    private String sender_action;

    public Member getRecipient() {
        return recipient;
    }

    public void setRecipient(Member recipient) {
        this.recipient = recipient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getSender_action() {
        return sender_action;
    }

    public void setSender_action(String sender_action) {
        this.sender_action = sender_action;
    }

    public FMReplyMessage withQuickReplies(List<QuickReply> quickReplies) {
        this.getMessage().withQuickReplies(quickReplies);
        return this;
    }

    public static class Message {

        private String text;
        private Attachment attachment;
        private List<QuickReply> quick_replies;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Attachment getAttachment() {
            return attachment;
        }

        public void setAttachment(Attachment attachment) {
            this.attachment = attachment;
        }

        public List<QuickReply> getQuick_replies() {
            return quick_replies;
        }

        public void setQuick_replies(List<QuickReply> quick_replies) {
            this.quick_replies = quick_replies;
        }

        public static class Attachment {

            private String type = "template";
            private Payload payload;

            public Payload getPayload() {
                return payload;
            }

            public void setPayload(Payload payload) {
                this.payload = payload;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class Payload {

                private TemplateType template_type = TemplateType.generic;
                private TopElementStyle top_element_style;
                private String text;
                private List<Button> buttons;
                private List<Element> elements;

                public TemplateType getTemplate_type() {
                    return template_type;
                }

                public void setTemplate_type(TemplateType template_type) {
                    this.template_type = template_type;
                }

                public TopElementStyle getTop_element_style() {
                    return top_element_style;
                }

                public void setTop_element_style(TopElementStyle top_element_style) {
                    this.top_element_style = top_element_style;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<Button> getButtons() {
                    return buttons;
                }

                public void setButtons(List<Button> buttons) {
                    this.buttons = buttons;
                }

                public List<Element> getElements() {
                    return elements;
                }

                public void setElements(List<Element> elements) {
                    this.elements = elements;
                }
            }
        }

        public Message withQuickReplies(List<QuickReply> quick_replies) {
            if (quick_replies == null) {
                return null;
            } else if (quick_replies != null && quick_replies.size() > QUICK_REPLY_MAX_SIZE) {
                throw new QuickReplyOutOfBoundException();
            }
            this.setQuick_replies(quick_replies);
            return this;
        }

        public Message withButtons(Button... buttons) {
            for (Button button : buttons) {
                this.getAttachment().getPayload().getButtons().add(button);
            }
            return this;
        }
    }

    public static class Member {
        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Member(String id) {
            this.id = id;
        }

        public Member() {
        }
    }

    public static class QuickReply {

        private QuickReplyType content_type;
        private String title;
        private String payload;
        private String image_url;

        public QuickReplyType getContent_type() {
            return content_type;
        }

        public void setContent_type(QuickReplyType content_type) {
            this.content_type = content_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public QuickReply() {

        }

        public QuickReply(String payload, String title, QuickReplyType content_type) {
            this.payload = payload;
            this.title = title;
            this.content_type = content_type;
        }
    }

    public static class Element {

        private String title;
        private String subtitle;
        private String item_url;
        private Button default_action;
        private String image_url;
        private List<Button> buttons;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getItem_url() {
            return item_url;
        }

        public void setItem_url(String item_url) {
            this.item_url = item_url;
        }

        public Button getDefault_action() {
            return default_action;
        }

        public void setDefault_action(Button default_action) {
            this.default_action = default_action;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        public void setButtons(List<Button> buttons) {
            this.buttons = buttons;
        }

        public Element withDefaultAction(Button button) {
            this.item_url = null;
            this.default_action = button;
            return this;
        }
    }

    public static class Button {

        private ButtonType type;//required
        private String url;//This URL is opened in a mobile browser when the button is tapped required
        private String title;//limit 20 required
        private FMEnum.WebViewHeightRatio webview_height_ratio;
        private Boolean messenger_extensions;
        private String fallback_url;//URL to use on clients that don't support Messenger Extensions. If this is not defined, the url will be used as the fallback.
        private String payload;

        public ButtonType getType() {
            return type;
        }

        public void setType(ButtonType type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public FMEnum.WebViewHeightRatio getWebview_height_ratio() {
            return webview_height_ratio;
        }

        public void setWebview_height_ratio(FMEnum.WebViewHeightRatio webview_height_ratio) {
            this.webview_height_ratio = webview_height_ratio;
        }

        public Boolean getMessenger_extensions() {
            return messenger_extensions;
        }

        public void setMessenger_extensions(Boolean messenger_extensions) {
            this.messenger_extensions = messenger_extensions;
        }

        public String getFallback_url() {
            return fallback_url;
        }

        public void setFallback_url(String fallback_url) {
            this.fallback_url = fallback_url;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }


        public static Button webUrlButton(String title, String url) {
            Button button = new Button();
            button.setType(ButtonType.web_url);
            button.setTitle(title);
            button.setUrl(url);
            return button;
        }

        public static Button postBackButton(String payload, String title) {
            Button button = new Button();
            button.setType(ButtonType.postback);
            button.setTitle(title);
            button.setPayload(payload);
            return button;
        }

        public static Button elementShareButton() {
            Button button = new Button();
            button.setType(ButtonType.element_share);
            return button;
        }

        public static Button loginButton(String authUrl) {
            Button button = new Button();
            button.setUrl(authUrl);
            button.setType(ButtonType.account_link);
            return button;
        }

        public static Button logoutButton() {
            Button button = new Button();
            button.setType(ButtonType.account_unlink);
            return button;
        }
    }

    public enum QuickReplyType {
        text, location
    }

    public enum TemplateType {
        button, generic, list
    }

    public enum ButtonType {
        web_url,
        postback,
        element_share,//Only individual message bubbles can be shared.
        account_link,
        account_unlink

    }

    public enum TopElementStyle {
        large,
        compact
    }

    public static final int TEXT_MAX_SIZE = 320;
    public static final int BUTTON_MAX_SIZE = 3;
    public static final int ELEMENT_MAX_SIZE = 10;
    public static final int TITLE_MAX_SIZE = 80;
    public static final int LIST_ELEMENT_MAX_SIZE = 4;
    public static final int LIST_ELEMENT_MIN_SIZE = 2;
    public static final int LIST_ELEMENT_BUTTON_MAX_SIZE = 1;
    public static final int QUICK_REPLY_MAX_SIZE = 10;
}
