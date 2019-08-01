package com.fm.example.demo.handler;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.message.FMMessageDeliveryHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/23.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class DeliveryHandler extends FMMessageDeliveryHandler {

    @Override
    public void handle(FMReceiveMessage.Messaging messaging) {
        log.info("DeliveryHandler handleDelivery, message -> {}", messaging);
    }
}
