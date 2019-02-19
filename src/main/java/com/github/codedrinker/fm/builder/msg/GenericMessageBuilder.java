package com.github.codedrinker.fm.builder.msg;

import com.github.codedrinker.fm.builder.attachment.GenericAttachmentBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;

/**
 * for generic template message
 * <p>
 * <pre class="prettyprint">
 *     FMReplyMessage msg = FMReplyMessageBuilder
 *                 .defaultBuilder(recipient)
 *                 .withMessage(GenericMessageBuilder
 *                         .defaultBuilder()
 *                         ...
 *                         .build())
 *                 .build();
 * </pre>
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class GenericMessageBuilder extends MessageBuilder {

    private GenericMessageBuilder() {
        super();
    }

    /**
     * create instance
     */
    public static GenericMessageBuilder defaultBuilder() {
        return new GenericMessageBuilder();
    }

    /**
     * set attachment for generic template
     *
     * @param builder {@link GenericAttachmentBuilder}
     * @return GenericMessageBuilder
     */
    public GenericMessageBuilder withAttachment(GenericAttachmentBuilder builder) {
        super.withAttachment(builder.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericMessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quick_replies) {
        super.withQuickReplies(quick_replies);
        return this;
    }
}
