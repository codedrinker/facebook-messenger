package com.github.codedrinker.fm.builder.element;

import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.exception.ButtonsOutOfBoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * {@link FMReplyMessage.Element} Helper
 *
 * @author wangwei on 2018/8/24.
 * wangwei@jiandaola.com
 */
public class ElementBuilder {

    protected FMReplyMessage.Element element;

    public ElementBuilder() {
        this.element = new FMReplyMessage.Element();
    }

    public static ElementBuilder defaultBuilder() {
        return new ElementBuilder();
    }

    /**
     * Set the title displayed in the template. No more than 80 characters
     */
    public ElementBuilder withTitle(String title) {
        if (StringUtils.length(title) > FMReplyMessage.TITLE_MAX_SIZE) {
            title = title.substring(0, 77) + "...";
        }
        this.element.setTitle(title);
        return this;
    }

    /**
     * Set the subtitles that appear in the template. No more than 80 characters
     */
    public ElementBuilder withSubtitle(String subtitle) {
        if (StringUtils.length(subtitle) > FMReplyMessage.TITLE_MAX_SIZE) {
            subtitle = subtitle.substring(0, 77) + "...";
        }
        this.element.setSubtitle(subtitle);
        return this;
    }

    /**
     * Set the URL that opens after clicking the bubble
     *
     * @deprecated
     */
    @Deprecated
    public ElementBuilder withItemUrl(String itemUrl) {
        this.element.setItem_url(itemUrl);
        return this;
    }

    /**
     * Set the image URL displayed in the template
     */
    public ElementBuilder withImageUrl(String imageUrl) {
        this.element.setImage_url(imageUrl);
        return this;
    }

    /**
     * Set the array of buttons to add to the template. Each element supports up to 3 buttons.
     */
    public ElementBuilder withButtons(FMReplyMessage.Button... buttons) {
        if (buttons == null || buttons.length == 0) {
            return this;
        }
        if (buttons.length > FMReplyMessage.BUTTON_MAX_SIZE) {
            throw new ButtonsOutOfBoundException();
        }
        if (this.element.getButtons() == null) {
            this.element.setButtons(new ArrayList<>(Arrays.asList(buttons)));
        } else {
            if (this.element.getButtons().size() + buttons.length > FMReplyMessage.BUTTON_MAX_SIZE) {
                throw new ButtonsOutOfBoundException();
            }
            this.element.getButtons().addAll(new ArrayList<>(Arrays.asList(buttons)));
        }
        return this;
    }

    /**
     * default action performed when when user taps the template or Messenger bubble
     *
     * @param defaultAction accept a button except for title attributes
     */
    public ElementBuilder withDefaultAction(FMReplyMessage.Button defaultAction) {
        this.element.setDefault_action(defaultAction);
        //noinspection deprecation
        withItemUrl(null);
        return this;
    }

    public FMReplyMessage.Element build() {
        return this.element;
    }
}
