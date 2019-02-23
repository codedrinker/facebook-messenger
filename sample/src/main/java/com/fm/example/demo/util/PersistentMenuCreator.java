package com.fm.example.demo.util;

import com.fm.example.demo.commands.GetStartedCommand;
import com.github.codedrinker.fm.builder.FMProfileSettingMessengeBuilder;
import com.github.codedrinker.fm.builder.menu.MenuItemBuilder;
import com.github.codedrinker.fm.builder.menu.PersistentMenuBuilder;
import com.github.codedrinker.fm.entity.FMEnum;
import com.github.codedrinker.fm.entity.FMProfileSettingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/23.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class PersistentMenuCreator {

    private GetStartedCommand startedCommand;

    @Autowired
    public PersistentMenuCreator(GetStartedCommand startedCommand) {
        this.startedCommand = startedCommand;
    }

    public FMProfileSettingMessage createPersistentMenu() {
        log.info("PersistentMenuCreator =>");
        FMProfileSettingMessage.MenuItem firstMenuItem = this.createFirstMenu();
        FMProfileSettingMessage.MenuItem secondMenuItem = this.createSecondMenu();
        FMProfileSettingMessage.MenuItem thirdMenuItem = this.createThirdMenu();
        FMProfileSettingMessage.PersistentMenu persistentMenu = PersistentMenuBuilder.defaultBuilder()
                .withMenu(firstMenuItem, secondMenuItem, thirdMenuItem)
                .build();
        return FMProfileSettingMessengeBuilder.defaultBuilder()
                .withPersistentMenu(persistentMenu)
                .build();
    }

    /**
     * 创建第一个菜单
     */
    private FMProfileSettingMessage.MenuItem createFirstMenu() {
        return MenuItemBuilder.defaultBuilder()
                .withTitle("Menu One")
                .withCallActionType(FMProfileSettingMessage.CallActionType.nested)
                .withSubMenu(
                        MenuItemBuilder
                                .defaultBuilder()
                                .withTitle("MENU_SUB_1")
                                .withCallActionType(FMProfileSettingMessage.CallActionType.postback)
                                .withPayload(startedCommand.command())
                                .build(),
                        MenuItemBuilder
                                .defaultBuilder()
                                .withTitle("MENU_SUB_2")
                                .withCallActionType(FMProfileSettingMessage.CallActionType.web_url)
                                .withUrl("https://www.google.com")
                                .withWebViewHeightRatio(FMEnum.WebViewHeightRatio.full)
                                .withMessengerExtensions(true)
                                .build()
                ).build();
    }

    private FMProfileSettingMessage.MenuItem createSecondMenu() {
        return MenuItemBuilder.defaultBuilder()
                .withUrl("https://m.baidu.com")
                .withTitle("Menu Second")
                .withCallActionType(FMProfileSettingMessage.CallActionType.web_url)
                .withWebViewHeightRatio(FMEnum.WebViewHeightRatio.full)
                .withMessengerExtensions(true)
                .build();
    }

    private FMProfileSettingMessage.MenuItem createThirdMenu() {
        return MenuItemBuilder.defaultBuilder()
                .withTitle("Menu Thirf")
                .withCallActionType(FMProfileSettingMessage.CallActionType.nested)
                .withSubMenu(
                        //二级菜单
                        MenuItemBuilder
                                .defaultBuilder()
                                .withTitle("MENU_SUB_2")
                                .withCallActionType(FMProfileSettingMessage.CallActionType.postback)
                                .withPayload(startedCommand.command())
                                .build(),
                        MenuItemBuilder
                                .defaultBuilder()
                                .withTitle("MENU_SUB_3")
                                .withCallActionType(FMProfileSettingMessage.CallActionType.web_url)
                                .withWebViewHeightRatio(FMEnum.WebViewHeightRatio.full)
                                .withMessengerExtensions(true)
                                .withUrl("http://aicoding.tech/")
                                .build()
                )
                .build();
    }
}
