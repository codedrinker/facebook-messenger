package com.github.codedrinker.fm.builder.msg;


import com.github.codedrinker.fm.builder.QuickReplyBuilder;
import com.github.codedrinker.fm.builder.attachment.ButtonAttachmentBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;

/**
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class ButtonMessageBuilder extends MessageBuilder {

    private ButtonMessageBuilder() {
    }

    /**
     * create instance
     */
    public static ButtonMessageBuilder defaultBuilder() {
        return new ButtonMessageBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quickReplies) {
        super.withQuickReplies(quickReplies);
        return this;
    }

    /**
     * add a quick reply
     *
     * @param builder {@link QuickReplyBuilder}
     * @see #withQuickReplies(FMReplyMessage.QuickReply...)
     */
    public ButtonMessageBuilder withQuickReply(QuickReplyBuilder builder) {
        withQuickReplies(builder.build());
        return this;
    }

    /**
     * set attachment for button template
     *
     * @param builder {@link ButtonAttachmentBuilder}
     * @return ButtonMessageBuilder
     */
    public ButtonMessageBuilder withAttachment(ButtonAttachmentBuilder builder) {
        super.withAttachment(builder.build());
        return this;
    }
}
