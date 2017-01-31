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
import com.github.codedrinker.fm.exception.ButtonsOutOfBoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class FMElementBuilder {

    private FMReplyMessage.Element element;

    public FMElementBuilder() {
        this.element = new FMReplyMessage.Element();
    }

    public static FMElementBuilder defaultBuilder() {
        return new FMElementBuilder();
    }

    public FMElementBuilder withTitle(String title) {
        if (StringUtils.length(title) > FMReplyMessage.TITLE_MAX_SIZE) {
            title = title.substring(0, 77) + "...";
        }
        this.element.setTitle(title);
        return this;
    }

    public FMElementBuilder withSubtitle(String subtitle) {
        if (StringUtils.length(subtitle) > FMReplyMessage.TITLE_MAX_SIZE) {
            subtitle = subtitle.substring(0, 77) + "...";
        }
        this.element.setSubtitle(subtitle);
        return this;
    }

    public FMElementBuilder withItemUrl(String itemUrl) {
        this.element.setItem_url(itemUrl);
        return this;
    }

    public FMElementBuilder withImageUrl(String imageUrl) {
        this.element.setImage_url(imageUrl);
        return this;
    }

    public FMElementBuilder withButtons(FMReplyMessage.Button... buttons) {
        if (buttons != null && buttons.length > FMReplyMessage.BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException();
        }
        if (this.element.getButtons() == null) {
            this.element.setButtons(new ArrayList<FMReplyMessage.Button>());
        }

        for (FMReplyMessage.Button button : buttons) {
            this.element.getButtons().add(button);
        }
        return this;
    }

    public FMReplyMessage.Element build() {
        return this.element;
    }
}
