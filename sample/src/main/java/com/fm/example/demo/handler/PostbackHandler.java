package com.fm.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.FMClient;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.command.FMCommandInvoker;
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.message.FMMessagePostBackHandler;
import com.github.codedrinker.fm.parser.FMCommandParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
