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

public class FMSettingMessage {
    private SettingType setting_type;
    private Greeting greeting;
    private ThreadState thread_state;
    private List<CallToAction> call_to_actions;

    public static class CallToAction {

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

        private String payload;
        private FMEnum.WebViewHeightRatio webview_height_ratio;
        private String url;
        private String title;
        private CallActionType type = CallActionType.postback;

        public CallToAction() {
        }

        public CallToAction(String payload) {
            this.payload = payload;
        }
    }

    public static class Greeting {
        private String text;
    }

    public enum SettingType {
        domain_whitelisting,
        greeting,
        call_to_actions //get started
    }

    public enum CallActionType {
        postback, web_url
    }

    public enum ThreadState {
        new_thread, existing_thread
    }

    private static final int GREETING_TEXT_MAX_SIZE = 160;
    private static final int CALL_TO_ACTIONS_MAX_SIZE = 5;
}
