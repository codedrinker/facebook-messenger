package com.github.codedrinker.fm.builder.attachment;


import com.github.codedrinker.fm.builder.element.ElementBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.exception.ButtonsOutOfBoundException;
import com.github.codedrinker.fm.exception.ElementsOutOfBoundException;

import java.util.ArrayList;

/**
 * Base Attachment Builder, you can choose a different subclass
 *
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 */
public class AttachmentBuilder {
    protected FMReplyMessage.Message.Attachment attachment;

    public AttachmentBuilder() {
        this.attachment = new FMReplyMessage.Message.Attachment();
        this.attachment.setPayload(new FMReplyMessage.Message.Attachment.Payload());
    }

    public static AttachmentBuilder defaultBuilder() {
        return new AttachmentBuilder();
    }

    /**
     * Set Attachment Type. If for template ,value must is {@link FMReplyMessage.Message.Attachment.AttachmentType#template}
     *
     * @param type default {@link FMReplyMessage.Message.Attachment.AttachmentType#template}
     */
    public AttachmentBuilder withAttachmentType(FMReplyMessage.Message.Attachment.AttachmentType type) {
        this.attachment.setType(type);
        return this;
    }

    /**
     * 直接给有效负载设置 text，当然可以{@link #withPayloadElements(FMReplyMessage.Element...)}
     * 如果是 常规模板 设置多个 element，会发送可水平滚动的模板轮播。最多支持 10 个元素。
     * <p>
     * 如果是 按钮模板 需要通过此方法 设置 有效负载
     */
    public AttachmentBuilder withPayloadText(String text) {
        this.attachment.getPayload().setText(text);
        return this;
    }

    /**
     * 在 常规模板下 使用Button 需要 放在 FMReplyMessage.Message.Attachment.Payload.Element 中
     * <p>
     * 在按钮模板下 使用Button 通过该方法配置
     *
     * @see ElementBuilder#withButtons(FMReplyMessage.Button...)
     */
    public AttachmentBuilder withPayloadButtons(FMReplyMessage.Button... buttons) {
        if (buttons == null || buttons.length == 0) {
            return this;
        }
        if (buttons.length > FMReplyMessage.BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException();
        }
        if (this.attachment.getPayload().getButtons() == null) {
            this.attachment.getPayload().setButtons(new ArrayList<FMReplyMessage.Button>());
        }

        for (FMReplyMessage.Button button : buttons) {
            this.attachment.getPayload().getButtons().add(button);
        }
        return this;
    }

    /**
     * Set element. If use in a {@link FMReplyMessage.TemplateType#generic} template, that describe an instance of a {@link FMReplyMessage.TemplateType#generic} template to send.
     * when multiple elements are specified, a template rotation that scrolls horizontally is sent. Supports up to 10 elements.、
     * <p>
     * If used in other templates, the number limits may vary, please see the different implementations
     */
    public AttachmentBuilder withPayloadElements(FMReplyMessage.Element... elements) {
        if (elements == null || elements.length == 0) {
            return this;
        }
        if (elements.length > FMReplyMessage.ELEMENT_MAX_SIZE) {
            throw new ElementsOutOfBoundException();
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
     * The aspect ratio used when specifying the image
     *
     * @param imageRatio default is {@link FMReplyMessage.ImageRatio#horizontal} - 1.91:1
     */
    @SuppressWarnings("UnusedReturnValue")
    public AttachmentBuilder withPayloadImageRatio(FMReplyMessage.ImageRatio imageRatio) {
        this.attachment.getPayload().setImage_aspect_ratio(imageRatio);
        return this;
    }

    /**
     * Under generic template，the value must be {@link FMReplyMessage.TemplateType#generic};
     * default is {@link FMReplyMessage.TemplateType#generic}
     */
    public AttachmentBuilder withPayloadType(FMReplyMessage.TemplateType type) {
        this.attachment.getPayload().setTemplate_type(type);
        return this;
    }

    /**
     * 设置为 true，可为模板消息启用 Messenger 原生分享按钮。默认为 false。
     * set whether the payload can be shared.
     * The Messenger native sharing button can be enabled for template messages.
     *
     * @param sharable true: share enable,otherwise disable; The default is false.
     */
    public AttachmentBuilder withPayloadSharable(boolean sharable) {
        this.attachment.getPayload().setSharable(sharable);
        return this;
    }

    /**
     * Set the format of the first list item, when "template_type = list"
     * <p>
     * <ul>
     * <li>large : Renders the first list item as a cover item.</li>
     * <li>compact : Renders an unformatted list item.</li>
     * </ul>
     * <p>
     * NOTE: Only for list template
     *
     * @param style {@link FMReplyMessage.TopElementStyle}
     * @return this instance
     */
    public AttachmentBuilder withPayloadTopElementStyle(FMReplyMessage.TopElementStyle style) {
        this.attachment.getPayload().setTop_element_style(style);
        return this;
    }

    /**
     * finish create {@link FMReplyMessage.Message.Attachment}
     *
     * @return {@link FMReplyMessage.Message.Attachment} instance
     */
    public FMReplyMessage.Message.Attachment build() {
        return this.attachment;
    }
}
