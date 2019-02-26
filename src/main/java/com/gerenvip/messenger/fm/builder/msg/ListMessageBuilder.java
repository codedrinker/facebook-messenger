package com.gerenvip.messenger.fm.builder.msg;

import com.gerenvip.messenger.fm.entity.FMReplyMessage;
import com.gerenvip.messenger.fm.builder.attachment.ListAttachmentBuilder;

/**
 * for list template message
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class ListMessageBuilder extends MessageBuilder {

    private ListMessageBuilder() {
    }

    /**
     * create instance
     */
    public static ListMessageBuilder defaultBuilder() {
        return new ListMessageBuilder();
    }

    /**
     * set attachment for list template
     *
     * @param builder {@link ListAttachmentBuilder}
     * @return ListAttachmentBuilder
     */
    public ListMessageBuilder withAttachment(ListAttachmentBuilder builder) {
        super.withAttachment(builder.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quick_replies) {
        super.withQuickReplies(quick_replies);
        return this;
    }

}
