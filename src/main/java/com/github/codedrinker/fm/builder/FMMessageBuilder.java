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
import com.github.codedrinker.fm.exception.TextOutOfBoundException;
import org.apache.commons.lang3.StringUtils;

public class FMMessageBuilder {

    private FMReplyMessage.Message message;

    public FMMessageBuilder() {
        this.message = new FMReplyMessage.Message();
    }

    public static FMMessageBuilder defaultBuilder() {
        return new FMMessageBuilder();
    }

    public FMMessageBuilder withText(String text) {
        if (StringUtils.isNotBlank(text) && text.length() > FMReplyMessage.TEXT_MAX_SIZE) {
            throw new TextOutOfBoundException();
        }
        this.message.setText(text);
        return this;
    }

    public FMMessageBuilder withAttachment(FMReplyMessage.Message.Attachment attachment) {
        this.message.setAttachment(attachment);
        return this;
    }

    public FMReplyMessage.Message build() {
        return this.message;
    }

}
