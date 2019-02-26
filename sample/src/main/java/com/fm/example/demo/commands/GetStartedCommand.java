package com.fm.example.demo.commands;

import com.gerenvip.messenger.fm.builder.FMReplyMessageBuilder;
import com.gerenvip.messenger.fm.builder.msg.TextMessageBuilder;
import com.gerenvip.messenger.fm.command.FMCommand;
import com.gerenvip.messenger.fm.entity.FMReplyMessage;
import com.gerenvip.messenger.fm.entity.RawFMUser;
import com.gerenvip.messenger.fm.parser.builtin.FMCommandDefaultParser;
import com.gerenvip.messenger.fm.provider.FMProvider;
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
