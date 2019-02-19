package com.github.codedrinker.fm.builder.msg;

import com.github.codedrinker.fm.builder.attachment.UploadAttachmentBuilder;
import com.github.codedrinker.fm.entity.FMUploadMessage;

/**
 * @author wangwei on 2018/9/7.
 * wangwei@jiandaola.com
 */
public class UploadMessageBuilder {

    private FMUploadMessage message;

    private UploadMessageBuilder() {
        message = new FMUploadMessage();
        message.setMessage(new FMUploadMessage.UploadMessage());
    }

    public static UploadMessageBuilder defaultBuilder() {
        return new UploadMessageBuilder();
    }


    /**
     * add upload attachment
     */
    public UploadMessageBuilder withAttachment(UploadAttachmentBuilder builder) {
        message.getMessage().setAttachment(builder.build());
        return this;
    }

    /**
     * add upload attachment
     *
     * @param type          one of image, video, audio, file
     * @param attachmentUrl attachment url
     * @param resue         Whether to reuse
     * @return this instance
     */
    public UploadMessageBuilder withAttachment(FMUploadMessage.UploadAttachmentType type, String attachmentUrl, boolean resue) {
        message.getMessage().setAttachment(UploadAttachmentBuilder
                .defaultBuilder()
                .withAttachmentType(type)
                .withAllowReuse(resue)
                .withAttachmentUrl(attachmentUrl)
                .build());
        return this;
    }

    public FMUploadMessage build() {
        return message;
    }
}
