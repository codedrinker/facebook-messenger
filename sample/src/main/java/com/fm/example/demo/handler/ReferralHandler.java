package com.fm.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.message.FMMessageReferralHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/23.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class ReferralHandler extends FMMessageReferralHandler {


    @Override
    public boolean canHandle(FMReceiveMessage.Messaging message) {
        if (message != null && message.getReferral() != null) {
            return true;
        }
        if (message != null && message.getPostback() != null && message.getPostback().getReferral() != null) {
            return true;
        }
        return false;
    }


    @Override
    public void handle(FMReceiveMessage.Messaging messaging) {
        if (messaging == null) {
            return;
        }
        log.info("ReferralHandler -> handle messaging {}", JSON.toJSONString(messaging));

        FMReceiveMessage.Messaging.Referral referral = null;
        if (messaging.getReferral() != null) {
            referral = messaging.getReferral();
        } else if (messaging.getPostback() != null && messaging.getPostback().getReferral() != null) {
            referral = messaging.getPostback().getReferral();
        }
        if (referral == null) {
            log.info("ReferralHandler -> handle null Referral");
            return;
        }
        String ref = referral.getRef();
        log.info("ref : {}", ref);
    }
}
