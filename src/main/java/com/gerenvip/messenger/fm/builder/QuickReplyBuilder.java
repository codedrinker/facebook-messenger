package com.gerenvip.messenger.fm.builder;

import com.gerenvip.messenger.fm.entity.FMReplyMessage;

/**
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 */
public class QuickReplyBuilder {

    public QuickReplyBuilder() {
        this.quickReply = new FMReplyMessage.QuickReply();
    }

    private FMReplyMessage.QuickReply quickReply;

    public static QuickReplyBuilder defaultBuilder() {
        return new QuickReplyBuilder();
    }

    /**
     * Mandatory if content_type is "text".
     * No more than 1000 characters.
     *
     * @param payload payload for Webhook  event
     * @return this instance
     */
    public QuickReplyBuilder withPayload(String payload) {
        this.quickReply.setPayload(payload);
        return this;
    }

    /**
     * Mandatory if content_type is "text". These texts will appear on the quick reply button. Do not exceed 20 characters
     *
     * @param title text display on the quick reply button
     * @return this instance
     */
    public QuickReplyBuilder withTitle(String title) {
        this.quickReply.setTitle(title);
        return this;
    }

    /**
     * Must be one of the following types:
     * <ul>
     * <li>{@link FMReplyMessage.QuickReplyType#text}: send text button. </li>
     * <li>{@link FMReplyMessage.QuickReplyType#location}: Send a button to collect the location information of the recipient of the message. </li>
     * <li>{@link FMReplyMessage.QuickReplyType#user_phone_number}: Sends a button to let the recipient of the message send the phone number bound to his account. </li>
     * <li>{@link FMReplyMessage.QuickReplyType#user_email}: Sends a button to let the recipient of the message send the mailbox bound to its account. </li>
     * </ul>
     *
     * @param type {@link FMReplyMessage.QuickReplyType}
     */
    public QuickReplyBuilder withContentType(FMReplyMessage.QuickReplyType type) {
        this.quickReply.setContent_type(type);
        return this;
    }

    /**
     * The URL of the image that appears on the text quick reply button.
     * The image size is no less than 24 X 24 pixels.
     * If the image is too large, the system will automatically crop and resize.
     *
     * @param iconUrl image URL. This must be set if title is an empty string.
     * @return this instance
     */
    public QuickReplyBuilder withIcon(String iconUrl) {
        this.quickReply.setImage_url(iconUrl);
        return this;
    }

    public FMReplyMessage.QuickReply build() {
        return quickReply;
    }
}
