package com.gerenvip.messenger.fm.builder;


import com.gerenvip.messenger.fm.builder.msg.ButtonMessageBuilder;
import com.gerenvip.messenger.fm.builder.msg.GenericMessageBuilder;
import com.gerenvip.messenger.fm.builder.msg.MessageBuilder;
import com.gerenvip.messenger.fm.builder.msg.TextMessageBuilder;
import com.gerenvip.messenger.fm.entity.FMReplyMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 */
public class FMReplyMessageBuilder {

    private FMReplyMessage fmpReplyMessage;

    public FMReplyMessageBuilder() {
        fmpReplyMessage = new FMReplyMessage();
    }

    /**
     * @see #defaultBuilder(String)
     */
    @Deprecated
    public static FMReplyMessageBuilder defaultBuilder() {
        return new FMReplyMessageBuilder();
    }

    /**
     * create instance with recipient
     *
     * @param recipient messenger recipient ID
     * @return this instance
     */
    public static FMReplyMessageBuilder defaultBuilder(String recipient) {
        FMReplyMessageBuilder builder = new FMReplyMessageBuilder();
        if (StringUtils.isNotBlank(recipient)) {
            builder.withRecipient(recipient);
        }
        return builder;
    }

    /**
     * create only text message
     */
    public static FMReplyMessageBuilder textBuilder(String recipient, String text) {
        return FMReplyMessageBuilder
                .defaultBuilder(recipient)
                .withMessage(
                        TextMessageBuilder
                                .defaultBuilder()
                                .withText(text)
                                .build());
    }

    /**
     * set recipient for this message
     */
    public FMReplyMessageBuilder withRecipient(String recipient) {
        fmpReplyMessage.setRecipient(new FMReplyMessage.Member(recipient));
        return this;
    }

    /**
     * set Message entity
     *
     * @param message {@link MessageBuilder}, {@link TextMessageBuilder}, {@link GenericMessageBuilder},{@link ButtonMessageBuilder}
     * @return this instance
     */
    public FMReplyMessageBuilder withMessage(FMReplyMessage.Message message) {
        fmpReplyMessage.setMessage(message);
        return this;
    }

    public FMReplyMessage build() {
        return fmpReplyMessage;
    }
}
