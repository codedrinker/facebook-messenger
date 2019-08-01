package com.gerenvip.messenger.fm.builder;


import com.gerenvip.messenger.fm.entity.FMEnum;
import com.gerenvip.messenger.fm.entity.FMReplyMessage;

/**
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 */
public class ButtonBuilder {

    private FMReplyMessage.Button button;

    public ButtonBuilder() {
        this.button = new FMReplyMessage.Button();
    }

    public static ButtonBuilder defaultBuilder() {
        return new ButtonBuilder();
    }

    /**
     * 创建 ButtonType 为 postback 的 ButtonBuilder
     */
    public static ButtonBuilder postbackBuilder() {
        return defaultBuilder()
                .withType(FMReplyMessage.ButtonType.postback);
    }

    /**
     * 创建 ButtonType 为 element_share 的 ButtonBuilder
     */
    public static ButtonBuilder shareBuilder() {
        return defaultBuilder()
                .withType(FMReplyMessage.ButtonType.element_share);
    }

    /**
     * 创建 ButtonType 为 web_url 的 ButtonBuilder
     */
    public static ButtonBuilder webUrlBuilder() {
        return defaultBuilder()
                .withType(FMReplyMessage.ButtonType.web_url);
    }

    /**
     * 不超过 20个字符
     * 注意，如果为 {@link FMReplyMessage.Element#default_action} 赋值，请勿设置 title 属性
     */
    public ButtonBuilder withTitle(String title) {
        this.button.setTitle(title);
        return this;
    }

    /**
     * 设置 url 默认是 ButtonType 是 type
     */
    public ButtonBuilder withUrl(String url) {
        this.button.setUrl(url);
        withType(FMReplyMessage.ButtonType.web_url);
        return this;
    }

    public ButtonBuilder withType(FMReplyMessage.ButtonType type) {
        this.button.setType(type);
        return this;
    }

    public ButtonBuilder withPayload(String payload) {
        this.button.setPayload(payload);
        return this;
    }

    public ButtonBuilder withWebViewHeightRatio(FMEnum.WebViewHeightRatio ratio) {
        this.button.setWebview_height_ratio(ratio);
        return this;
    }

    public ButtonBuilder withMessengerExtensions(boolean enable) {
        this.button.setMessenger_extensions(enable);
        return this;
    }

    public ButtonBuilder withFallbackUrl(String fallbackUrl) {
        this.button.setFallback_url(fallbackUrl);
        return this;
    }

    /**
     * 当按钮具有分享功能时，设置分享的内容的 Attachment，
     * 设置分享内容会自动将 button 的 type 设置为 element_share
     */
    public ButtonBuilder withShare(FMReplyMessage.ShareContent share) {
        this.button.setShare_contents(share);
        withType(FMReplyMessage.ButtonType.element_share);
        return this;
    }

    /**
     * 网址按钮打开的网页是否隐藏分享按钮
     */
    public ButtonBuilder withHidenWebViewShare(boolean hidden) {
        if (hidden) {
            this.button.setWebview_share_button("hide");
        }
        return this;
    }

    public FMReplyMessage.Button build() {
        return this.button;
    }
}
