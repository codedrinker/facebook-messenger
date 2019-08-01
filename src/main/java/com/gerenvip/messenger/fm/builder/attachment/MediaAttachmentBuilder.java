package com.gerenvip.messenger.fm.builder.attachment;


import com.gerenvip.messenger.fm.builder.element.MediaElementBuilder;
import com.gerenvip.messenger.fm.entity.FMReplyMessage;
import com.gerenvip.messenger.fm.exception.ElementsOutOfBoundException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class MediaAttachmentBuilder extends AttachmentBuilder {

    public MediaAttachmentBuilder() {
        super();
        forceSetPayloadTemplateType();
    }

    /**
     * create builder instance
     */
    public static MediaAttachmentBuilder defaultBuilder() {
        return new MediaAttachmentBuilder();
    }

    /**
     * Under Media Template， template_type must be {@link FMReplyMessage.TemplateType#media}.
     * force set.
     */
    private void forceSetPayloadTemplateType() {
        withPayloadType(FMReplyMessage.TemplateType.media);
    }

    /**
     * implement {@link AttachmentBuilder#withPayloadType(FMReplyMessage.TemplateType)}， force set type with {@link FMReplyMessage.TemplateType#media}
     *
     * @param type {@link FMReplyMessage.TemplateType },you can set anything of {FMReplyMessage.TemplateType}，but it is invalid
     * @return this instance
     */
    @Override
    public MediaAttachmentBuilder withPayloadType(FMReplyMessage.TemplateType type) {
        super.withPayloadType(FMReplyMessage.TemplateType.media);
        return this;
    }

    /**
     * An array containing 1 element object that describe the media in the message. A maximum of 1 element is supported.
     *
     * @param elements {@link MediaElementBuilder}
     * @see #withPayloadElement(MediaElementBuilder)
     */
    @Override
    public MediaAttachmentBuilder withPayloadElements(FMReplyMessage.Element... elements) {
        if (elements == null || elements.length == 0) {
            return this;
        }
        if (elements.length > FMReplyMessage.MEDIA_ELEMENT_MAX_SIZE) {
            throw new ElementsOutOfBoundException("payload elements size must <= " + FMReplyMessage.MEDIA_ELEMENT_MAX_SIZE);
        }

        if (this.attachment.getPayload().getElements() == null) {
            this.attachment.getPayload().setElements(new ArrayList<>(Arrays.asList(elements)));
        } else {
            if (this.attachment.getPayload().getElements().size() + elements.length > FMReplyMessage.MEDIA_ELEMENT_MAX_SIZE) {
                throw new ElementsOutOfBoundException("payload elements size must <= " + FMReplyMessage.MEDIA_ELEMENT_MAX_SIZE);
            }
            this.attachment.getPayload().getElements().addAll(new ArrayList<>(Arrays.asList(elements)));
        }

        return this;
    }

    /**
     * An array containing 1 element object that describe the media in the message. A maximum of 1 element is supported.
     *
     * @param builder {@link MediaElementBuilder}
     * @see #withPayloadElements(FMReplyMessage.Element...)
     */
    public MediaAttachmentBuilder withPayloadElement(MediaElementBuilder builder) {
        withPayloadElements(builder.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MediaAttachmentBuilder withPayloadSharable(boolean sharable) {
        super.withPayloadSharable(sharable);
        return this;
    }
}
