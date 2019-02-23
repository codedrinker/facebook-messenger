package com.fm.example.demo.commands;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.builder.FMReplyMessageBuilder;
import com.github.codedrinker.fm.builder.msg.TextMessageBuilder;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.entity.RawFMUser;
import com.github.codedrinker.fm.parser.builtin.FMCommandDefaultParser;
import com.github.codedrinker.fm.provider.FMProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2018/8/20.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component("getStartedCommand")
public class GetStartedCommand implements FMCommand {


    @Override
    public String command(String... params) {
        return FMCommandDefaultParser.getDefault().toCommand("GETSTARTED", params);
    }

    @Override
    public void execute(String recipient, String... params) {

        log.info("GetStartedCommand -> execute ");

        RawFMUser user = FMProvider.getUserProfile(recipient);

        String text = user != null ? "Hello " + user.getLast_name() : "Hello";
        FMReplyMessage startMessage = FMReplyMessageBuilder
                .defaultBuilder(recipient)
                .withMessage(TextMessageBuilder
                        .defaultBuilder()
                        .withText(text)
                        .build())
                .build();
        FMProvider.sendMessage(startMessage);
    }
}
