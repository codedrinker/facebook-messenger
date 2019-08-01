package com.gerenvip.messenger.fm.builder.msg;


import com.gerenvip.messenger.fm.entity.FMReplyMessage;
import com.gerenvip.messenger.fm.builder.attachment.GenericAttachmentBuilder;
import com.gerenvip.messenger.fm.builder.attachment.MediaAttachmentBuilder;

/**
 * for media template message
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class MediaMessageBuilder extends MessageBuilder {

    public MediaMessageBuilder() {
    }

    /**
     * create instance
     */
    public static MediaMessageBuilder defaultBuilder() {
        return new MediaMessageBuilder();
    }

    /**
     * set attachment for media template
     *
     * @param builder {@link GenericAttachmentBuilder}
     * @return GenericMessageBuilder
     */
    public MediaMessageBuilder withAttachment(MediaAttachmentBuilder builder) {
        super.withAttachment(builder.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quickReplies) {
        return super.withQuickReplies(quickReplies);
    }
}
