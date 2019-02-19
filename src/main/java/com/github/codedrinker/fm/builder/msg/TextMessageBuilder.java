package com.github.codedrinker.fm.builder.msg;

import com.github.codedrinker.fm.entity.FMReplyMessage;

/**
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class TextMessageBuilder extends MessageBuilder {

    private TextMessageBuilder() {
        super();
    }

    public static TextMessageBuilder defaultBuilder() {
        return new TextMessageBuilder();
    }

    /**
     * set normal text message content
     *
     * @param text message content
     * @return {@link TextMessageBuilder} this
     */
    @Override
    public TextMessageBuilder withText(String text) {
        super.withText(text);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextMessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quickReplies) {
        super.withQuickReplies(quickReplies);
        return this;
    }
}
