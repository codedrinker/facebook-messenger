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

/**
 * @see FMProfileSettingMessage
 */
@Deprecated
public class FMSettingMessage {
    private SettingType setting_type;
    private Greeting greeting;
    private ThreadState thread_state;
    private List<CallToAction> call_to_actions;

    public SettingType getSetting_type() {
        return setting_type;
    }

    public void setSetting_type(SettingType setting_type) {
        this.setting_type = setting_type;
    }

    public Greeting getGreeting() {
        return greeting;
    }

    public void setGreeting(Greeting greeting) {
        this.greeting = greeting;
    }

    public ThreadState getThread_state() {
        return thread_state;
    }

    public void setThread_state(ThreadState thread_state) {
        this.thread_state = thread_state;
    }

    public List<CallToAction> getCall_to_actions() {
        return call_to_actions;
    }

    public void setCall_to_actions(List<CallToAction> call_to_actions) {
        this.call_to_actions = call_to_actions;
    }

    @Deprecated
    public static class CallToAction {

        private String payload;
        private FMEnum.WebViewHeightRatio webview_height_ratio;
        private String url;
        private String title;
        private CallActionType type = CallActionType.postback;
        private List<CallToAction> call_to_actions;

        public List<CallToAction> getCall_to_actions() {
            return call_to_actions;
        }

        /**
         * 设置嵌套 CallToAction -- 嵌套menu 使用
         * 如果设置嵌套 menu 需 保证 type 为 nested
         */
        public void setCall_to_actions(List<CallToAction> call_to_actions) {
            this.type = CallActionType.nested;
            this.call_to_actions = call_to_actions;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

        public FMEnum.WebViewHeightRatio getWebview_height_ratio() {
            return webview_height_ratio;
        }

        public void setWebview_height_ratio(FMEnum.WebViewHeightRatio webview_height_ratio) {
            this.webview_height_ratio = webview_height_ratio;
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

        public CallActionType getType() {
            return type;
        }

        public void setType(CallActionType type) {
            this.type = type;
        }

        public CallToAction(String payload, FMEnum.WebViewHeightRatio webview_height_ratio, String url, String title) {
            this.payload = payload;
            this.webview_height_ratio = webview_height_ratio;
            this.url = url;
            this.title = title;
        }

        public CallToAction(String payload, FMEnum.WebViewHeightRatio webview_height_ratio, String title) {
            this.payload = payload;
            this.webview_height_ratio = webview_height_ratio;
            this.title = title;
        }

        public CallToAction(CallActionType type, String url, String title) {
            this.type = type;
            this.url = url;
            this.title = title;
        }

        public CallToAction(String payload, String title) {
            this.payload = payload;
            this.title = title;
        }

        public CallToAction() {
        }

        public CallToAction(String payload) {
            this.payload = payload;
        }
    }

    @Deprecated
    public static class Greeting {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    @Deprecated
    public enum SettingType {
        domain_whitelisting,
        greeting,
        call_to_actions //get started
    }

    @Deprecated
    public enum CallActionType {
        postback,
        web_url,
        /**
         * 嵌套菜单使用
         */
        nested
    }

    @Deprecated
    public enum ThreadState {
        new_thread, existing_thread
    }

    private static final int GREETING_TEXT_MAX_SIZE = 160;
    private static final int CALL_TO_ACTIONS_MAX_SIZE = 5;
}
