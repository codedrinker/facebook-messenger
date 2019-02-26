package com.gerenvip.messenger.fm.builder.menu;

import com.gerenvip.messenger.fm.entity.FMProfileSettingMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangwei on 2018/8/22.
 * wangwei@jiandaola.com
 */
public class PersistentMenuBuilder {
    private FMProfileSettingMessage.PersistentMenu persistentMenu;

    public PersistentMenuBuilder() {
        this.persistentMenu = new FMProfileSettingMessage.PersistentMenu();
    }

    public static PersistentMenuBuilder defaultBuilder() {
        return new PersistentMenuBuilder();
    }

    @SuppressWarnings("Duplicates")
    public PersistentMenuBuilder withMenu(FMProfileSettingMessage.MenuItem... menus) {
        if (menus != null && menus.length > 0) {
            List<FMProfileSettingMessage.MenuItem> subList = new ArrayList<FMProfileSettingMessage.MenuItem>(Arrays.asList(menus));
            if (persistentMenu.getCall_to_actions() == null) {
                persistentMenu.setCall_to_actions(subList);
            } else {
                persistentMenu.getCall_to_actions().addAll(subList);
            }
        }
        return this;
    }

    /**
     * 设置locale 信息，例如: en_US,zh_CN,zh_TW,zh_HK
     */
    public PersistentMenuBuilder withLocale(String locale) {
        persistentMenu.setLocale(locale);
        return this;
    }

    /**
     * 设置为 true 时，禁用 Messenger 编写工具。也就是说，用户只能通过固定菜单、回传、按钮和网页视图与您的智能助手互动。
     * 默认 false
     */
    public PersistentMenuBuilder withComposerInputDisabled(boolean disabled) {
        persistentMenu.setComposer_input_disabled(disabled);
        return this;
    }

    public FMProfileSettingMessage.PersistentMenu build() {
        return this.persistentMenu;
    }
}
