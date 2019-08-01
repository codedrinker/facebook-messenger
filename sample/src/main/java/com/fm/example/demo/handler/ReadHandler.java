package com.fm.example.demo.handler;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.message.FMMessageReadHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/23.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class ReadHandler extends FMMessageReadHandler {

    @Override
    public void handle(FMReceiveMessage.Messaging messaging) {
        log.info("FMPReadHandler handleDelivery, message -> {}", messaging);
    }
}
