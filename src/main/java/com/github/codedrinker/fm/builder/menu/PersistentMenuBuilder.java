package com.github.codedrinker.fm.builder.menu;

import com.github.codedrinker.fm.entity.FMProfileSettingMessage.*;
import com.github.codedrinker.fm.entity.FMProfileSettingMessage.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangwei on 2018/8/22.
 * wangwei@jiandaola.com
 */
public class PersistentMenuBuilder {
    private PersistentMenu persistentMenu;

    public PersistentMenuBuilder() {
        this.persistentMenu = new PersistentMenu();
    }

    public static PersistentMenuBuilder defaultBuilder() {
        return new PersistentMenuBuilder();
    }

    @SuppressWarnings("Duplicates")
    public PersistentMenuBuilder withMenu(MenuItem... menus) {
        if (menus != null && menus.length > 0) {
            List<MenuItem> subList = new ArrayList<MenuItem>(Arrays.asList(menus));
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

    public PersistentMenu build() {
        return this.persistentMenu;
    }
}
