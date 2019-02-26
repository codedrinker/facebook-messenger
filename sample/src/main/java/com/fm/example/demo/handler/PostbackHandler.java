package com.fm.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.gerenvip.messenger.fm.FMClient;
import com.gerenvip.messenger.fm.command.FMCommand;
import com.gerenvip.messenger.fm.command.FMCommandInvoker;
import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.message.FMMessagePostBackHandler;
import com.gerenvip.messenger.fm.parser.FMCommandParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/23.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class PostbackHandler extends FMMessagePostBackHandler {

    @Override
    public void handle(FMReceiveMessage.Messaging messaging) {
        log.info("PostbackHandler -> handle, sender -> {}, postback -> {}", JSON.toJSONString(messaging.getSender()), JSON.toJSONString(messaging));

        FMCommandParser parser = FMClient.getInstance().getFmCommandParser();
        FMCommandParser parse = parser.parse(messaging.getPostback().getPayload());

        FMCommand command = FMCommandInvoker.getInstance().invoke(parse);

        String id = messaging.getSender().getId();
        command.execute(id, parse.getParams());
    }
}
