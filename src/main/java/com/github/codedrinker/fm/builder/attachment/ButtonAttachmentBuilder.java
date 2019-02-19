package com.github.codedrinker.fm.builder.attachment;


import com.github.codedrinker.fm.builder.ButtonBuilder;
import com.github.codedrinker.fm.builder.msg.ButtonMessageBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;

/**
 * for button template message 's attachment
 * use in {@link ButtonMessageBuilder}
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class ButtonAttachmentBuilder extends AttachmentBuilder {

    private ButtonAttachmentBuilder() {
        super();
        forceSetPayloadTemplateType();
    }

    /**
     * create builder instance
     */
    public static ButtonAttachmentBuilder defaultBuilder() {
        return new ButtonAttachmentBuilder();
    }

    /**
     * Under Button Template， template_type must be {@link FMReplyMessage.TemplateType#button}.
     * force set.
     */
    private void forceSetPayloadTemplateType() {
        withPayloadType(FMReplyMessage.TemplateType.button);
    }

    /**
     * implement {@link AttachmentBuilder#withPayloadType(FMReplyMessage.TemplateType)}， force set type with {@link FMReplyMessage.TemplateType#button}
     * {@inheritDoc}
     *
     * @param type {@link FMReplyMessage.TemplateType },you can set anything of {FMReplyMessage.TemplateType}，but it is invalid
     * @return this instance
     */
    @Override
    public ButtonAttachmentBuilder withPayloadType(FMReplyMessage.TemplateType type) {
        super.withPayloadType(FMReplyMessage.TemplateType.button);
        return this;
    }

    /**
     * Text is displayed above the button.
     * UTF-8 encoded text, up to 640 characters.
     */
    @Override
    public ButtonAttachmentBuilder withPayloadText(String text) {
        super.withPayloadText(text);
        return this;
    }

    /**
     * Set a set of buttons that appear as action calls, including 1-3 buttons
     */
    @Override
    public ButtonAttachmentBuilder withPayloadButtons(FMReplyMessage.Button... buttons) {
        super.withPayloadButtons(buttons);
        return this;
    }

    /**
     * Set a action Button with ButtonBuilder
     */
    public ButtonAttachmentBuilder withPayloadButtons(ButtonBuilder buttonBuilder) {
        withPayloadButtons(buttonBuilder.build());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ButtonAttachmentBuilder withPayloadSharable(boolean sharable) {
        super.withPayloadSharable(sharable);
        return this;
    }
}
