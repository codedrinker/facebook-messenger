package com.fm.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.FMClient;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.command.FMCommandInvoker;
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.message.FMMessageHandler;
import com.github.codedrinker.fm.parser.FMCommandParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/19.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class MessageHandler extends FMMessageHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleMessage(FMReceiveMessage.Messaging messaging) {
        String id = messaging.getSender().id;
        FMReceiveMessage.Messaging.Message message = messaging.getMessage();
        FMCommandParser parser = FMClient.getInstance().getFmCommandParser();
        FMCommandParser parse = parser.parse(message.getText());
        FMCommand command = FMCommandInvoker.getInstance().invoke(parse);
        command.execute(id, parse.getParams());
    }

    /**
     * 处理 QuickReply 消息
     *
     * @param messaging  {@link FMReceiveMessage.Messaging}
     * @param quickReply {@link FMReceiveMessage.Messaging.Message.QuickReply}
     */
    @Override
    protected void handleQuickReply(FMReceiveMessage.Messaging messaging, FMReceiveMessage.Messaging.Message.QuickReply quickReply) {
        String payload = quickReply.getPayload();
        FMCommandParser parser = FMClient.getInstance().getFmCommandParser();
        FMCommandParser parse = parser.parse(payload);

        FMCommand command = FMCommandInvoker.getInstance().invoke(parse);
        command.execute(messaging.getSender().id, parse.getParams());
    }

}
