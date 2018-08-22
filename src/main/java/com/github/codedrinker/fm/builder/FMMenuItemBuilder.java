package com.github.codedrinker.fm.builder;

import com.github.codedrinker.fm.entity.FMEnum;
import com.github.codedrinker.fm.entity.FMProfileSettingMessage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangwei on 2018/8/22.
 * wangwei@jiandaola.com
 */
public class FMMenuItemBuilder {
    private MenuItem menuItem;

    public FMMenuItemBuilder() {
        this.menuItem = new MenuItem();
    }

    public static FMMenuItemBuilder defaultBuilder() {
        return new FMMenuItemBuilder();
    }

    public FMMenuItemBuilder withCallActionType(CallActionType type) {
        this.menuItem.setType(type);
        return this;
    }

    public FMMenuItemBuilder withTitle(String title) {
        this.menuItem.setTitle(title);
        return this;
    }

    public FMMenuItemBuilder withUrl(String url) {
        this.menuItem.setUrl(url);
        return this;
    }

    public FMMenuItemBuilder withPayload(String payload) {
        this.menuItem.setPayload(payload);
        return this;
    }

    @SuppressWarnings("Duplicates")
    public FMMenuItemBuilder withSubMenu(MenuItem... subMenus) {
        if (subMenus != null && subMenus.length > 0) {
            List<MenuItem> subList = new ArrayList<MenuItem>(Arrays.asList(subMenus));
            if (menuItem.getCall_to_actions() == null) {
                menuItem.setCall_to_actions(subList);
            } else {
                menuItem.getCall_to_actions().addAll(subList);
            }
        }
        return this;
    }

    public FMMenuItemBuilder withWebViewHeightRatio(FMEnum.WebViewHeightRatio ratio) {
        menuItem.setWebview_height_ratio(ratio);
        return this;
    }

    /**
     * 如果菜单项类型为 web_url 且 Messenger 功能插件 SDK 将在网页视图中使用，则该对象必须为 true
     * 可选参数
     */
//    public FMMenuItemBuilder withMessengerExtensions(boolean messenger_extensions) {
//        menuItem.setMessenger_extensions(messenger_extensions);
//        return this;
//    }
    public FMMenuItemBuilder withMessengerExtensionsFallbackUrl(String fallbackUrl) {
        menuItem.setFallback_url(fallbackUrl);
        return this;
    }

    public FMMenuItemBuilder withHidenShareBtn(boolean hidden) {
        if (hidden) {
            menuItem.setWebview_share_button("hide");
        }
        return this;
    }

    public MenuItem build() {
        return menuItem;
    }
}
