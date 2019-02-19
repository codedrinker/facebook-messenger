package com.github.codedrinker.fm.builder.msg;

import com.github.codedrinker.fm.builder.attachment.ListAttachmentBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;

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
