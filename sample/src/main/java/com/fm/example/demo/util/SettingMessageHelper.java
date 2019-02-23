package com.fm.example.demo.util;

import com.fm.example.demo.commands.GetStartedCommand;
import com.github.codedrinker.fm.builder.FMProfileSettingMessengeBuilder;
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
public class SettingMessageHelper {

    /**
     * 这是 messenger 上 page 的欢迎语
     */
    public FMProfileSettingMessage greetingSetting() {
        log.info("greetingSetting =>");
        return FMProfileSettingMessengeBuilder
                .defaultBuilder()
                .withGreeting("Hi {{user_first_name}}, We're happy to have you here! ❤️\n")
                .build();
    }

    /**
     * 设置进入聊天列表中的欢迎开始语
     */
    public FMProfileSettingMessage getStartedSetting() {
        log.info("getStartedSetting =>");
        return FMProfileSettingMessengeBuilder
                .defaultBuilder()
                .withGetStartedPayload(getStartedCommand.command())
                .build();
    }

    private GetStartedCommand getStartedCommand;

    @Autowired
    public SettingMessageHelper(GetStartedCommand getStartedCommand) {
        this.getStartedCommand = getStartedCommand;
    }
}
