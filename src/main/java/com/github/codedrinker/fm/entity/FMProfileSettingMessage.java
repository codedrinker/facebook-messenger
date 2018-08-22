package com.github.codedrinker.fm.entity;

import java.util.List;

/**
 * 设置和更新 Messenger Profile，例如  Persistent Menu，Get Started Button
 * ，Welcome Page ...
 *
 * @author wangwei on 2018/8/22.
 * wangwei@jiandaola.com
 */
public class FMProfileSettingMessage {

    private static final String DEFAULT_LOCALE = "default";
    private List<Greeting> greeting;
    /**
     * 身份验证回调URL。必须使用https协议
     */
    private String account_linking_url;

    private List<PersistentMenu> persistent_menu;

    private Payload get_started;

    public List<Greeting> getGreeting() {
        return greeting;
    }

    /**
     * set 主页欢迎语
     */
    public void setGreeting(List<Greeting> greeting) {
        this.greeting = greeting;
    }

    public String getAccount_linking_url() {
        return account_linking_url;
    }

    /**
     * set 身份验证回调URL
     */
    public void setAccount_linking_url(String account_linking_url) {
        this.account_linking_url = account_linking_url;
    }

    public List<PersistentMenu> getPersistent_menu() {
        return persistent_menu;
    }

    /**
     * set persistent menu
     *
     * @param persistent_menu 不是多个一级菜单，如果要根据不同的locale 显示不同的menu，可以配置多个。一般情况数据中一个PersistentMenu
     */
    public void setPersistent_menu(List<PersistentMenu> persistent_menu) {
        this.persistent_menu = persistent_menu;
    }

    public Payload getGet_started() {
        return get_started;
    }

    /**
     * set 消息视图下的欢迎语
     */
    public void setGet_started(Payload get_started) {
        this.get_started = get_started;
    }

    public static class PersistentMenu {

        private String locale = DEFAULT_LOCALE;
        /**
         * 设置为 true 时，禁用 Messenger 编写工具。也就是说，用户只能通过固定菜单、回传、按钮和网页视图与您的智能助手互动。
         * 默认 false
         */
        private boolean composer_input_disabled;

        /**
         * 固定菜单的顶层菜单项组成的数组
         * 最多可有三个菜单项
         * 最多可有两个嵌套菜单。
         */
        private List<MenuItem> call_to_actions;

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public boolean isComposer_input_disabled() {
            return composer_input_disabled;
        }

        public void setComposer_input_disabled(boolean composer_input_disabled) {
            this.composer_input_disabled = composer_input_disabled;
        }

        public List<MenuItem> getCall_to_actions() {
            return call_to_actions;
        }

        public void setCall_to_actions(List<MenuItem> call_to_actions) {
            this.call_to_actions = call_to_actions;
        }
    }

    public static class MenuItem extends Payload {
        private CallActionType type;
        /**
         * 显示在菜单项上的标题。不超过 30 个字符。
         */
        private String title;
        /**
         * 轻触网址按钮后将打开的网址。如果类型为 web_url，则必须提供
         */
        private String url;

        /**
         * 嵌套的 menu_item（菜单项），将在下一层级展开。最多可有五个菜单项。如果类型为 nested，则必须提供。一个固定菜单最多可有两个嵌套菜单。
         */
        private List<MenuItem> call_to_actions;

        /**
         * 网页视图的高度,可选参数
         */
        private FMEnum.WebViewHeightRatio webview_height_ratio;

        /**
         * 如果菜单项类型为 web_url 且 Messenger 功能插件 SDK 将在网页视图中使用，则该对象必须为 true
         * 可选参数
         */
        private boolean messenger_extensions;

        /**
         * 可选参数
         * 客户端不支持 Messenger 功能插件 SDK 时，将在网页视图中打开此网址。如果没有定义，url 将作为回退网址。仅当设置为 "messenger_extensions": true 时，才指定此对象。
         */
        private String fallback_url;

        /**
         * 如果设置为 hide 会 禁止 网页视图顶部的分享按钮（适用于敏感信息）
         */
        private String webview_share_button;

        public CallActionType getType() {
            return type;
        }

        public void setType(CallActionType type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<MenuItem> getCall_to_actions() {
            return call_to_actions;
        }

        /**
         * 如果设置了嵌套菜单，会自动将 type 改为 nested
         */
        public void setCall_to_actions(List<MenuItem> call_to_actions) {
            this.call_to_actions = call_to_actions;
            this.type = CallActionType.nested;
        }

        public FMEnum.WebViewHeightRatio getWebview_height_ratio() {
            return webview_height_ratio;
        }

        public void setWebview_height_ratio(FMEnum.WebViewHeightRatio webview_height_ratio) {
            this.webview_height_ratio = webview_height_ratio;
        }

        public boolean isMessenger_extensions() {
            return messenger_extensions;
        }

        public void setMessenger_extensions(boolean messenger_extensions) {
            this.messenger_extensions = messenger_extensions;
        }

        public String getFallback_url() {
            return fallback_url;
        }

        public void setFallback_url(String fallback_url) {
            this.fallback_url = fallback_url;
        }

        public String getWebview_share_button() {
            return webview_share_button;
        }

        public void setWebview_share_button(String webview_share_button) {
            this.webview_share_button = webview_share_button;
        }
    }


    public enum CallActionType {
        postback,
        web_url,
        /**
         * 嵌套菜单使用
         */
        nested
    }

    /**
     * 首次与智能助手交互的用户会看到欢迎页面
     */
    public static class Greeting {
        /**
         * 欢迎语，例如 "Hello" 或 "Hello {{user_first_name}}!"
         * 可使用的 模板字符串:
         * <li>
         * <ul>{{user_first_name}}</ul>
         * <ul>{{user_last_name}}</ul>
         * <ul>{{user_full_name}}</ul>
         * </li>
         */
        private String text;
        /**
         * locale 信息，例如: en_US,zh_CN,zh_TW,zh_HK
         */
        private String locale = DEFAULT_LOCALE;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }
    }

    public static class Payload {
        /**
         * 将以 messaging_postbacks 事件的形式发回给 Webhook 的数据。如果类型为 postback，则必须提供。不超过 1000 个字符
         */
        private String payload;

        public Payload() {
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }

}
