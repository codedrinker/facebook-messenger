package com.gerenvip.messenger.fm.entity;

import com.gerenvip.messenger.fm.exception.QuickReplyOutOfBoundException;
import lombok.Data;

import java.util.List;

/**
 * Message entity that interacts with messenger server
 * <p>
 * https://developers.facebook.com/docs/messenger-platform/reference/send-api#message
 *
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 */
@Data
public class FMReplyMessage {
    /**
     * 消息类型
     *
     * @see MessageType
     */
    //private String messaging_type;
    private Member recipient;
    private Message message;
    /**
     * 使用发送 API 设置输入提示或发送阅读回执，让用户知道您正在处理他们的请求。
     */
    @Deprecated
    private String sender_action;

    @Data
    public static class Message {
        /**
         * 纯文本类型只需要这一个字段,需要文本限制 必须采用 UTF-8 编码格式，字符数限制为 2000 个。
         * 您无法同时发送 text 和 attachment
         */
        private String text;
        /**
         * 预览网址。用于发送包含媒体内容的消息或结构化消息。
         */
        private Attachment attachment;
        /**
         * 要随消息发送的一组 quick_reply
         */
        private List<QuickReply> quick_replies;

        @Data
        public static class Attachment {
            /**
             * 附件类型
             */
            private AttachmentType type = AttachmentType.template;
            /**
             * 附件负载
             */
            private Payload payload;

            /**
             * 支持的附件类型
             */
            public enum AttachmentType {
                template,
                image,
                video,
                audio,
                file
            }

            @Data
            public static class Payload {
                /**
                 * 常规模板中 必须是 generic 类型
                 */
                private TemplateType template_type = TemplateType.generic;
                private boolean sharable = false;
                private ImageRatio image_aspect_ratio;
                /**
                 * 列表模板 即 template_type = list 会使用
                 * <p>
                 * Set the format of the first list item.
                 * <ul>
                 * <li>large : Renders the first list item as a cover item.</li>
                 * <li>compact : Renders an unformatted list item.</li>
                 * </ul>
                 */
                private TopElementStyle top_element_style;
                /**
                 * 有效负载 text 消息，也可以不设置，而使用 elements,例如常规模板可以直接使用elements
                 * 而 按钮模板 使用 text 字段
                 */
                private String text;

                /**
                 * 按钮模板下启用
                 */
                private List<Button> buttons;
                /**
                 * 描述要发送的常规模板的实例。指定多个元素时，会发送可水平滚动的模板轮播。最多支持 10 个元素。
                 */
                private List<Element> elements;

                /**
                 * 要在消息中附加收藏的素材，请在消息请求的 payload.attachment_id 属性中指定素材的 attachment_id,
                 * 此时 template_type 应该为 null，Attachment.type 应该是  image, video, audio, file 中的一种类型
                 */
                private String attachment_id;
            }
        }

        public Message withQuickReplies(List<QuickReply> quick_replies) {
            if (quick_replies == null) {
                return this;
            }
            if (quick_replies.size() > QUICK_REPLY_MAX_SIZE) {
                throw new QuickReplyOutOfBoundException();
            }
            this.setQuick_replies(quick_replies);
            return this;
        }
    }

    /**
     * 消息接收人
     */
    @Data
    public static class Member {
        public Member(String id) {
            this.id = id;
        }

        public Member() {
        }

        public String id;
//        public String phone_number;
//        public String user_ref;
//        public String name;
    }

    @Data
    public static class QuickReply {
        /**
         * text 或 location,user_phone_number,user_email
         */
        private QuickReplyType content_type;
        private String title;
        private String payload;
        private String image_url;

        public QuickReply() {

        }

        public QuickReply(String payload, String title, QuickReplyType content_type) {
            this.payload = payload;
            this.title = title;
            this.content_type = content_type;
        }
    }

    @Data
    public static class Element {
        /**
         * 气泡标题 的字符数限制为 80 个
         */
        private String title;
        /**
         * 气泡副标题 的字符数限制为 80 个
         */
        private String subtitle;
        /**
         * 轻触气泡打开的网址
         */
        @Deprecated
        private String item_url;
        /**
         * 用户轻触元素时要触发的默认操作 基本可以复用button 类,但是不能与item_url同时出现,Button中的title 属性无效
         */
        private Button default_action;
        /**
         * 显示在模板中的图片网址
         */
        private String image_url;
        /**
         * 要添加到模板中的按钮数组。每个元素最多支持 3 个按钮。
         */
        private List<Button> buttons;

        /**
         * only for media template
         */
        private MediaTemplate_MediaType media_type;
        /**
         * The attachment ID of the image or video. Cannot be used if url is set.
         * how to get "attachment_id"? you can upload your media file by Upload API
         * (https://graph.facebook.com/v2.6/me/message_attachments?access_token=<PAGE_ACCESS_TOKEN>)
         */
        private String attachment_id;

        /**
         * The URL of the image. Cannot be used if attachment_id is set. must be FACEBOOK_URL
         */
        private String url;

        /**
         * button 中不能带  title 属性
         */
        public Element withDefaultAction(Button button) {
            this.item_url = null;
            this.default_action = button;
            return this;
        }
    }

    @Data
    public static class ShareContent {
        private Message.Attachment attachment;
    }

    @Data
    public static class Button {
        /**
         * required
         */
        private ButtonType type;
        /**
         * This URL is opened in a mobile browser when the button is tapped required
         */
        private String url;
        /**
         * limit 20 required
         */
        private String title;
        private FMEnum.WebViewHeightRatio webview_height_ratio;
        /**
         * 如果菜单项类型为 web_url 且 Messenger 功能插件 SDK 将在网页视图中使用，则该对象必须为 true
         * 可选参数
         */
        private Boolean messenger_extensions;
        /**
         * URL to use on clients that don't support Messenger Extensions.
         * If this is not defined, the url will be used as the fallback.
         */
        private String fallback_url;

        /**
         * 仅限于postback时候使用
         */
        private String payload;

        private ShareContent share_contents;

        /**
         * 网址按钮和菜单项会有这个属性
         * 同 {@link FMProfileSettingMessage.MenuItem#webview_share_button}
         */
        private String webview_share_button;


        public static Button webUrlButton(String title, String url) {
            Button button = new Button();
            button.setType(ButtonType.web_url);
            button.setTitle(title);
            button.setUrl(url);
            return button;
        }

        public static Button postBackButton(String payload, String title) {
            Button button = new Button();
            button.setType(ButtonType.postback);
            button.setTitle(title);
            button.setPayload(payload);
            return button;
        }

        public static Button elementShareButton() {
            Button button = new Button();
            button.setType(ButtonType.element_share);
            return button;
        }

        public static Button loginButton(String authUrl) {
            Button button = new Button();
            button.setUrl(authUrl);
            button.setType(ButtonType.account_link);
            return button;
        }

        public static Button logoutButton() {
            Button button = new Button();
            button.setType(ButtonType.account_unlink);
            return button;
        }
    }

    /**
     * 消息类型
     * <p>
     * https://developers.facebook.com/docs/messenger-platform/send-messages/#messaging_types
     */
    public enum MessageType {
        /**
         * 消息是为了回复收到的消息而发送
         */
        RESPONSE("RESPONSE"),
        /**
         * 消息为主动发送，并非回复收到的消息。
         * 这包括在 24 小时标准消息发送时间窗以内或根据 24+1 政策发送的推广类和非推广类消息。
         */
        UPDATE("UPDATE"),
        /**
         * 消息不具有推广性质，属于在 24 小时标准消息发送时间窗以外发送，带有消息标签。
         */
        MESSAGE_TAG("MESSAGE_TAG");
        String value;

        MessageType(String value) {
            this.value = value;
        }
    }

    public enum QuickReplyType {
        text,
        location,
        /**
         * 如果用户的个人主页中没有手机号，则快速回复不会显示
         */
        user_phone_number,
        /**
         * 如果用户的个人主页中没有电子邮箱，则快速回复不会显示
         */
        user_email
    }

    /**
     * 消息模板类型
     */
    public enum TemplateType {
        generic,
        list,
        button,
        /**
         * 媒体模板
         */
        media,
        /**
         * 开放图谱模板
         */
        open_graph
    }

    /**
     * Currently, media templates only support sending pictures and videos, and currently do not support sending audio.
     */
    public enum MediaTemplate_MediaType {
        image,
        video
    }

    /**
     * 按钮类型
     */
    public enum ButtonType {
        web_url,
        postback,
        /**
         * Only individual message bubbles can be shared.
         */
        element_share,
        account_link,
        account_unlink

    }

    public enum TopElementStyle {
        /**
         * 第一个图很大的那种
         */
        large,
        /**
         * 所有的图都一样的平展
         */
        compact
    }

    /**
     * 图片显示的宽高比
     */
    public enum ImageRatio {
        /**
         * 1.91:1
         * default
         */
        horizontal,
        square
    }

    public static final int TEXT_MAX_SIZE = 320;
    /**
     * 要添加到模板中的按钮数组。每个元素最多支持 3 个按钮。
     */
    public static final int BUTTON_MAX_SIZE = 3;

    /**
     * 指定多个元素时，会发送可水平滚动的模板轮播。最多支持 10 个元素。
     */
    public static final int ELEMENT_MAX_SIZE = 10;
    public static final int MEDIA_ELEMENT_MAX_SIZE = 1;
    public static final int MEDIA_BUTTON_MAX_SIZE = 1;
    /**
     * 显示在模板中的标题。不超过 80 个字符。
     */
    public static final int TITLE_MAX_SIZE = 80;
    public static final int LIST_ELEMENT_MAX_SIZE = 4;
    public static final int LIST_ELEMENT_MIN_SIZE = 2;
    public static final int LIST_ELEMENT_BUTTON_MAX_SIZE = 1;
    /**
     * 最多可定义 11 个快速回复按钮的对象
     */
    public static final int QUICK_REPLY_MAX_SIZE = 11;
}
