package com.github.codedrinker.fm.builder.element;


import com.github.codedrinker.fm.builder.attachment.MediaAttachmentBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.exception.ButtonsOutOfBoundException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * for media template message 's element
 * use in {@link MediaAttachmentBuilder}
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class MediaElementBuilder extends ElementBuilder {

    private MediaElementBuilder() {
    }

    public static MediaElementBuilder defaultBuilder() {
        return new MediaElementBuilder();
    }

    /**
     * set media type for media template
     *
     * @param meidaType The type of media being sent - image or video is supported.
     * @return this instance
     */
    public MediaElementBuilder withMeidaType(FMReplyMessage.MediaTemplate_MediaType meidaType) {
        this.element.setMedia_type(meidaType);
        return this;
    }

    /**
     * Set a attachment ID of the image or video. Cannot be used if url is set.
     * <p>
     * how to get "attachment_id"? you can upload your media file by Upload API
     * (https://graph.facebook.com/v2.6/me/message_attachments?access_token=<PAGE_ACCESS_TOKEN>)
     *
     * @param attachmentId attachment_id which get from Upload API
     * @return this instance
     */
    public MediaElementBuilder withAttachmentId(String attachmentId) {
        this.element.setAttachment_id(attachmentId);
        return this;
    }

    /**
     * The URL of the image. Cannot be used if attachment_id is set. must be FACEBOOK_URL
     *
     * @param facebookMediaUrl URL for the media file on Facebook
     * @return this instance
     */
    public MediaElementBuilder withFacebookMediaUrl(String facebookMediaUrl) {
        this.element.setUrl(facebookMediaUrl);
        return this;
    }

    /**
     * An array of button objects to be appended to the template. A maximum of 1 button is supported.
     *
     * @param buttons {@link FMReplyMessage.Button}
     * @return this instance
     * @see #withButton(FMReplyMessage.Button)
     */
    @Override
    public MediaElementBuilder withButtons(FMReplyMessage.Button... buttons) {
        if (buttons == null || buttons.length == 0) {
            return this;
        }
        if (buttons.length > FMReplyMessage.MEDIA_BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException();
        }
        if (this.element.getButtons() == null) {
            this.element.setButtons(new ArrayList<>(Arrays.asList(buttons)));
        } else {
            if (this.element.getButtons().size() + buttons.length > FMReplyMessage.MEDIA_BUTTON_MAX_SIZE) {
                throw new ButtonsOutOfBoundException();
            }
            this.element.getButtons().addAll(new ArrayList<>(Arrays.asList(buttons)));
        }
        return this;
    }

    /**
     * Set a Button for Media template
     *
     * @param button {@link FMReplyMessage.Button}
     * @return this instance
     */
    public MediaElementBuilder withButton(FMReplyMessage.Button button) {
        withButtons(button);
        return this;
    }

}
