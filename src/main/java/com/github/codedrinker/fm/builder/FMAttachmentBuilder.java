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
import com.github.codedrinker.fm.exception.ElementsOutOfBoundException;

import java.util.ArrayList;

public class FMAttachmentBuilder {
    private FMReplyMessage.Message.Attachment attachment;

    public FMAttachmentBuilder() {
        this.attachment = new FMReplyMessage.Message.Attachment();
        this.attachment.setPayload(new FMReplyMessage.Message.Attachment.Payload());
    }

    public static FMAttachmentBuilder defaultBuilder() {
        return new FMAttachmentBuilder();
    }

    public FMAttachmentBuilder withText(String text) {
        this.attachment.getPayload().setText(text);
        return this;
    }

    public FMAttachmentBuilder withButtons(FMReplyMessage.Button... buttons) {
        if (buttons != null && buttons.length > FMReplyMessage.BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException();
        }
        if (this.attachment.getPayload().getButtons() == null) {
            this.attachment.getPayload().setButtons(new ArrayList<FMReplyMessage.Button>());
        }

        for (FMReplyMessage.Button button : buttons) {
            this.attachment.getPayload().getButtons().add(button);
        }
        return this;
    }

    public FMAttachmentBuilder withElements(FMReplyMessage.Element... elements) {
        if (elements != null && elements.length > FMReplyMessage.ELEMENT_MAX_SIZE) {
            throw new ElementsOutOfBoundException();
        }

        if (this.attachment.getPayload().getElements() == null) {
            this.attachment.getPayload().setElements(new ArrayList<FMReplyMessage.Element>());
        }

        for (FMReplyMessage.Element element : elements) {
            this.attachment.getPayload().getElements().add(element);
        }

        withType(FMReplyMessage.TemplateType.generic);
        return this;
    }

    public FMAttachmentBuilder withType(FMReplyMessage.TemplateType type) {
        this.attachment.getPayload().setTemplate_type(type);
        return this;
    }

    public FMReplyMessage.Message.Attachment build() {
        return this.attachment;
    }
}
