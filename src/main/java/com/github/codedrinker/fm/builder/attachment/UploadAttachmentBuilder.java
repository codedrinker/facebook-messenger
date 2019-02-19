package com.github.codedrinker.fm.builder.attachment;


import com.github.codedrinker.fm.entity.FMUploadMessage;

/**
 * @author wangwei on 2018/9/7.
 * wangwei@jiandaola.com
 */
public class UploadAttachmentBuilder {

    private FMUploadMessage.UploadAttachment attachment;

    private UploadAttachmentBuilder() {
        this.attachment = new FMUploadMessage.UploadAttachment();
        this.attachment.setPayload(new FMUploadMessage.UploadAttachment.UploadPaylod());
    }

    public static UploadAttachmentBuilder defaultBuilder() {
        return new UploadAttachmentBuilder();
    }

    /**
     * The type of the attachment. Must be one of the following:
     * <ul>
     * <li>image</li>
     * <li>video</li>
     * <li>audio</li>
     * <li>file</li>
     * </ul>
     */
    public UploadAttachmentBuilder withAttachmentType(FMUploadMessage.UploadAttachmentType type) {
        this.attachment.setType(type);
        return this;
    }

    public UploadAttachmentBuilder withAllowReuse(boolean reuse) {
        this.attachment.getPayload().set_reusable(reuse);
        return this;
    }

    public UploadAttachmentBuilder withAttachmentUrl(String url) {
        this.attachment.getPayload().setUrl(url);
        return this;
    }

    public FMUploadMessage.UploadAttachment build() {
        return attachment;
    }
}
