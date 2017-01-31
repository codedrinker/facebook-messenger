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

import java.util.ArrayList;

public class FMReplyMessageBuilder {
    private FMReplyMessage fmpReplyMessage;

    public FMReplyMessage getFmpReplyMessage() {
        return fmpReplyMessage;
    }

    public void setFmpReplyMessage(FMReplyMessage fmpReplyMessage) {
        this.fmpReplyMessage = fmpReplyMessage;
    }

    public FMReplyMessageBuilder() {
        fmpReplyMessage = new FMReplyMessage();
    }

    public static FMReplyMessageBuilder defaultBuilder() {
        return new FMReplyMessageBuilder();
    }

    public static FMReplyMessageBuilder textBuilder(String recipient, String text) {
        FMReplyMessageBuilder fmpReplyMessageBuilder = new FMReplyMessageBuilder();
        FMReplyMessage fmpReplyMessage = new FMReplyMessage();
        fmpReplyMessage.setRecipient(new FMReplyMessage.Member(recipient));
        fmpReplyMessage.setMessage(FMMessageBuilder.defaultBuilder().withText(text).build());
        fmpReplyMessageBuilder.setFmpReplyMessage(fmpReplyMessage);
        return fmpReplyMessageBuilder;
    }

    public FMReplyMessageBuilder withRecipient(String recipient) {
        fmpReplyMessage.setRecipient(new FMReplyMessage.Member(recipient));
        return this;
    }

    public FMReplyMessageBuilder withMessage(FMReplyMessage.Message message) {
        fmpReplyMessage.setMessage(message);
        return this;
    }

    public FMReplyMessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quickReplies) {
        if (fmpReplyMessage.getMessage().getQuick_replies() == null) {
            fmpReplyMessage.getMessage().setQuick_replies(new ArrayList<FMReplyMessage.QuickReply>());
        }
        for (FMReplyMessage.QuickReply quickReply : quickReplies) {
            fmpReplyMessage.getMessage().getQuick_replies().add(quickReply);
        }
        return this;
    }

    public FMReplyMessage build() {
        return this.getFmpReplyMessage();
    }
}
