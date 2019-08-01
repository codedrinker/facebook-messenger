package com.gerenvip.messenger.fm.builder.attachment;


import com.gerenvip.messenger.fm.builder.msg.GenericMessageBuilder;
import com.gerenvip.messenger.fm.entity.FMReplyMessage;
import com.gerenvip.messenger.fm.builder.element.ElementBuilder;

/**
 * for generic template message 's attachment
 * use in {@link GenericMessageBuilder}
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class GenericAttachmentBuilder extends AttachmentBuilder {

    private GenericAttachmentBuilder() {
        super();
        forceSetPayloadTemplateType();
    }

    /**
     * create builder instance
     */
    public static GenericAttachmentBuilder defaultBuilder() {
        return new GenericAttachmentBuilder();
    }

    /**
     * Under Generic Template， template_type must be {@link FMReplyMessage.TemplateType#generic}.
     * force set.
     */
    private void forceSetPayloadTemplateType() {
        withPayloadType(FMReplyMessage.TemplateType.generic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericAttachmentBuilder withPayloadSharable(boolean sharable) {
        super.withPayloadSharable(sharable);
        return this;
    }

    /**
     * implement {@link AttachmentBuilder#withPayloadType(FMReplyMessage.TemplateType)}， force set type with {@link FMReplyMessage.TemplateType#generic}
     *
     * @param type {@link FMReplyMessage.TemplateType },you can set anything of {FMReplyMessage.TemplateType}，but it is invalid
     * @return this instance
     */
    @Override
    public GenericAttachmentBuilder withPayloadType(FMReplyMessage.TemplateType type) {
        super.withPayloadType(FMReplyMessage.TemplateType.generic);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericAttachmentBuilder withPayloadImageRatio(FMReplyMessage.ImageRatio imageRatio) {
        super.withPayloadImageRatio(imageRatio);
        return this;
    }

    /**
     * {@inheritDoc}
     * max 10 items
     */
    @Override
    public GenericAttachmentBuilder withPayloadElements(FMReplyMessage.Element... elements) {
        super.withPayloadElements(elements);
        return this;
    }

    /**
     * set element with {@link ElementBuilder}
     *
     * @param builder {@link ElementBuilder}
     * @return this instance
     * @see #withPayloadElements(FMReplyMessage.Element...)
     */
    public GenericAttachmentBuilder withPayloadElements(ElementBuilder builder) {
        withPayloadElements(builder.build());
        return this;
    }
}
