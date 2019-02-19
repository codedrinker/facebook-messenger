package com.github.codedrinker.fm.builder.element;

import com.github.codedrinker.fm.builder.attachment.ListAttachmentBuilder;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.exception.ButtonsOutOfBoundException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * for list template message 's element
 * * use in {@link ListAttachmentBuilder}
 *
 * @author wangwei on 2018/9/5.
 * wangwei@jiandaola.com
 */
public class ListElementBuilder extends ElementBuilder {

    public ListElementBuilder() {
    }

    public static ListElementBuilder defaultBuilder() {
        return new ListElementBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListElementBuilder withTitle(String title) {
        super.withTitle(title);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListElementBuilder withSubtitle(String subtitle) {
        super.withSubtitle(subtitle);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListElementBuilder withImageUrl(String imageUrl) {
        super.withImageUrl(imageUrl);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param defaultAction accept a button except for title attributes
     *                      Allowed only if the messenger_extensions property is set to true
     */
    @Override
    public ListElementBuilder withDefaultAction(FMReplyMessage.Button defaultAction) {
        super.withDefaultAction(defaultAction);
        // Allowed only if the messenger_extensions property is set to true
        defaultAction.setMessenger_extensions(true);
        return this;
    }

    /**
     * The button to display in the list item. Supports up to 1 button.
     *
     * @param buttons max size is 1
     * @see #withButton(FMReplyMessage.Button)
     */
    @Override
    public ListElementBuilder withButtons(FMReplyMessage.Button... buttons) {
        if (buttons == null || buttons.length == 0) {
            return this;
        }
        if (buttons.length > FMReplyMessage.LIST_ELEMENT_BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException("buttons size must <= " + FMReplyMessage.LIST_ELEMENT_BUTTON_MAX_SIZE);
        }
        if (this.element.getButtons() == null) {
            this.element.setButtons(new ArrayList<>(Arrays.asList(buttons)));
        } else {
            if (this.element.getButtons().size() + buttons.length > FMReplyMessage.LIST_ELEMENT_BUTTON_MAX_SIZE) {
                throw new ButtonsOutOfBoundException();
            }
            this.element.getButtons().addAll(new ArrayList<>(Arrays.asList(buttons)));
        }
        return this;
    }

    /**
     * Set the button to display in the list item.
     *
     * @see #withButtons(FMReplyMessage.Button...)
     */
    public ListElementBuilder withButton(FMReplyMessage.Button button) {
        super.withButtons(button);
        return this;
    }
}
