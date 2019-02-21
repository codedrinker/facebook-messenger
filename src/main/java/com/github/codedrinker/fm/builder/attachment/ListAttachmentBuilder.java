package com.github.codedrinker.fm.builder.attachment;


import com.github.codedrinker.fm.builder.element.ListElementBuilder;
import com.github.codedrinker.fm.builder.msg.ListMessageBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.exception.ButtonsOutOfBoundException;
import com.github.codedrinker.fm.exception.ElementsOutOfBoundException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * for list template message 's attachment
 * use in {@link ListMessageBuilder}
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class ListAttachmentBuilder extends AttachmentBuilder {

    private ListAttachmentBuilder() {
        super();
        forceSetPayloadTemplateType();
    }

    /**
     * create builder instance
     */
    public static ListAttachmentBuilder defaultBuilder() {
        return new ListAttachmentBuilder();
    }

    /**
     * Under List Template， template_type must be {@link FMReplyMessage.TemplateType#generic}.
     * force set.
     */
    private void forceSetPayloadTemplateType() {
        withPayloadType(FMReplyMessage.TemplateType.list);
    }

    /**
     * implement {@link AttachmentBuilder#withPayloadType(FMReplyMessage.TemplateType)}， force set type with {@link FMReplyMessage.TemplateType#list}
     *
     * @param type {@link FMReplyMessage.TemplateType },you can set anything of {FMReplyMessage.TemplateType}，but it is invalid
     * @return this instance
     */
    @Override
    public ListAttachmentBuilder withPayloadType(FMReplyMessage.TemplateType type) {
        super.withPayloadType(FMReplyMessage.TemplateType.list);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListAttachmentBuilder withPayloadSharable(boolean sharable) {
        super.withPayloadSharable(sharable);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListAttachmentBuilder withPayloadTopElementStyle(FMReplyMessage.TopElementStyle style) {
        super.withPayloadTopElementStyle(style);
        return this;
    }

    /**
     * The button that appears at the bottom of the list. Supports up to 1 button.
     */
    @Override
    public ListAttachmentBuilder withPayloadButtons(FMReplyMessage.Button... buttons) {
        if (buttons == null || buttons.length == 0) {
            return this;
        }
        if (buttons.length > FMReplyMessage.LIST_ELEMENT_BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException();
        }
        if (this.attachment.getPayload().getButtons() == null) {
            this.attachment.getPayload().setButtons(new ArrayList<>(Arrays.asList(buttons)));
        } else {
            if (this.attachment.getPayload().getButtons().size() + buttons.length > FMReplyMessage.LIST_ELEMENT_BUTTON_MAX_SIZE) {
                throw new ButtonsOutOfBoundException();
            }
            this.attachment.getPayload().getButtons().addAll(new ArrayList<>(Arrays.asList(buttons)));
        }
        return this;
    }

    /**
     * The button that appears at the bottom of the list. Supports up to 1 button.
     */
    public ListAttachmentBuilder withPayloadButton(FMReplyMessage.Button button) {
        withPayloadButtons(button);
        return this;
    }

    /**
     * Requires a minimum of 2 elements. Supports up to 4 elements.
     *
     * @param elements {@link ListElementBuilder}
     * @return this instance
     */
    @Override
    public ListAttachmentBuilder withPayloadElements(FMReplyMessage.Element... elements) {
        if (elements == null || elements.length == 0) {
            return this;
        }
        if (elements.length > FMReplyMessage.LIST_ELEMENT_MAX_SIZE) {
            throw new ElementsOutOfBoundException("payload elements size must <= " + FMReplyMessage.LIST_ELEMENT_MAX_SIZE);
        }

        if (this.attachment.getPayload().getElements() == null) {
            this.attachment.getPayload().setElements(new ArrayList<FMReplyMessage.Element>());
        }

        for (FMReplyMessage.Element element : elements) {
            this.attachment.getPayload().getElements().add(element);
        }
        return this;
    }

    /**
     * check elements size
     */
    private void checkElementsCount() {
        if (this.attachment.getPayload().getElements() == null
                || this.attachment.getPayload().getElements().size() < FMReplyMessage.LIST_ELEMENT_MIN_SIZE
                || this.attachment.getPayload().getElements().size() > FMReplyMessage.LIST_ELEMENT_MAX_SIZE) {
            throw new ElementsOutOfBoundException("Under list template, payload elements length must >= 2 and <= 4 ");
        }
    }

    @Override
    public FMReplyMessage.Message.Attachment build() {
        checkElementsCount();
        return super.build();
    }
}
