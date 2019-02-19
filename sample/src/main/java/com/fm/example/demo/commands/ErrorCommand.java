package com.fm.example.demo.commands;

import com.github.codedrinker.fm.builder.FMReplyMessageBuilder;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.parser.FMCommandParser;
import com.github.codedrinker.fm.parser.builtin.FMCommandDefaultParser;
import com.github.codedrinker.fm.provider.FMProvider;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/19.
 * wangwei@jiandaola.com
 */
@Component("errorCommand")
public class ErrorCommand implements FMCommand {

    private static final String ERROR_COMMAND_NAME = "ERROR";

    /**
     * 返回一个带参数的 Command 有效负载。
     * 一般会调用 {@link FMCommandParser#toCommand(String, String...)}
     * 方法 ，通过编码生成一个 payload 字符串
     *
     * @param params Command 参数
     * @return payload 字符串
     */
    @Override
    public String command(String... params) {
        return FMCommandDefaultParser.getDefault().toCommand(ERROR_COMMAND_NAME, params);
    }

    /**
     * 执行 该 Command
     *
     * @param recipient 消息接收者Id，就是和 Bold 对话的 用户Id
     * @param params    有效负载中携带的 参数信息
     */
    @Override
    public void execute(String recipient, String... params) {
        FMReplyMessage replyMessage = FMReplyMessageBuilder.textBuilder(recipient, "Error Command execute ...").build();

        FMProvider.sendMessage(replyMessage);
    }
}
