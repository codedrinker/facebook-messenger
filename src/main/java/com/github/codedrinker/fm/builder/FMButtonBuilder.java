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
package com.github.codedrinker.fm.builder;

import com.github.codedrinker.fm.entity.FMReplyMessage;

public class FMButtonBuilder {

    private FMReplyMessage.Button button;

    public FMButtonBuilder() {
        this.button = new FMReplyMessage.Button();
    }

    public static FMButtonBuilder defaultBuilder() {
        return new FMButtonBuilder();
    }

    public static FMButtonBuilder postbackBuilder() {
        return defaultBuilder()
                .withType(FMReplyMessage.ButtonType.postback);
    }

    public static FMButtonBuilder shareBuilder() {
        return defaultBuilder()
                .withType(FMReplyMessage.ButtonType.element_share);
    }

    public FMButtonBuilder withTitle(String title) {
        this.button.setTitle(title);
        return this;
    }

    public FMButtonBuilder withUrl(String url) {
        this.button.setUrl(url);
        return this;
    }

    public FMButtonBuilder withType(FMReplyMessage.ButtonType type) {
        this.button.setType(type);
        return this;
    }

    public FMButtonBuilder withPayload(String payload) {
        this.button.setPayload(payload);
        return this;
    }

    public FMReplyMessage.Button build() {
        return this.button;
    }
}
