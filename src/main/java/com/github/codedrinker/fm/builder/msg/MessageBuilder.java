package com.github.codedrinker.fm.builder.msg;


import com.github.codedrinker.fm.builder.QuickReplyBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.exception.QuickReplyOutOfBoundException;
import com.github.codedrinker.fm.exception.TextOutOfBoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Base Message Builder,you can choose a different subclass
 *
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 * @see GenericMessageBuilder
 */
public class MessageBuilder {

    protected FMReplyMessage.Message message;

    public MessageBuilder() {
        this.message = new FMReplyMessage.Message();
    }

    public static MessageBuilder defaultBuilder() {
        return new MessageBuilder();
    }

    /**
     * 一个消息中 不能同时发送 Text 和 Attachment
     */
    public MessageBuilder withText(String text) {
        if (StringUtils.isNotBlank(text) && text.length() > FMReplyMessage.TEXT_MAX_SIZE) {
            throw new TextOutOfBoundException();
        }
        this.message.setText(text);
        return this;
    }

    /**
     * 一个消息中 不能同时发送 Text 和 Attachment
     */
    public MessageBuilder withAttachment(FMReplyMessage.Message.Attachment attachment) {
        this.message.setAttachment(attachment);
        return this;
    }

    /**
     * Set a quick reply button for the message to be sent. Up to 11 quick responses are supported.
     *
     * @param quickReplies {@link QuickReplyBuilder}
     */
    public MessageBuilder withQuickReplies(FMReplyMessage.QuickReply... quickReplies) {
        if (quickReplies == null || quickReplies.length == 0) {
            return this;
        }
        if (quickReplies.length > FMReplyMessage.QUICK_REPLY_MAX_SIZE) {
            throw new QuickReplyOutOfBoundException();
        }

        if (message.getQuick_replies() == null) {
            this.message.withQuickReplies(new ArrayList<>(Arrays.asList(quickReplies)));
        } else {
            if (this.message.getQuick_replies().size() + quickReplies.length > FMReplyMessage.QUICK_REPLY_MAX_SIZE) {
                throw new QuickReplyOutOfBoundException();
            }
            this.message.getQuick_replies().addAll(new ArrayList<>(Arrays.asList(quickReplies)));
        }
        return this;
    }

    public FMReplyMessage.Message build() {
        return this.message;
    }

}
