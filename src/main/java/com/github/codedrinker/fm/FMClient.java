/*
 * Copyright 2017 Chunlei Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.codedrinker.fm;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.aspect.FMDefaultResultAspect;
import com.github.codedrinker.fm.aspect.FMResultAspect;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.command.FMCommandInvoker;
import com.github.codedrinker.fm.command.FMDefaultCommand;
import com.github.codedrinker.fm.entity.*;
import com.github.codedrinker.fm.exception.AccessSecretUndefinedException;
import com.github.codedrinker.fm.handler.*;
import com.github.codedrinker.fm.parser.FMCommandDefaultParser;
import com.github.codedrinker.fm.parser.FMCommandParser;
import com.github.codedrinker.fm.provider.FMProvider;
import com.github.codedrinker.fm.utils.Signature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FMClient {
    Logger logger = LoggerFactory.getLogger(FMClient.class);

    private FMCommandParser fmCommandParser;
    private boolean isCommandEnabled = false;
    private String accessToken;
    private String accessSecret;
    private FMResultAspect fmResultAspect;

    private FMMessageDeliveryHandler fmMessageDeliveryHandler;
    private FMMessageHandler fmMessageHandler;
    private FMMessagePostBackHandler fmMessagePostBackHandler;
    private FMMessageReadHandler fmMessageReadHandler;
    private FMMessageReferralHandler fmMessageReferralHandler;

    private static FMClient client;

    private FMClient() {
        this.fmResultAspect = new FMDefaultResultAspect();
        setDefaultHandler();
    }

    public static FMClient getInstance() {
        if (client == null) {
            client = new FMClient();
        }
        return client;
    }

    public void enableCommand() {
        this.isCommandEnabled = true;
        this.fmCommandParser = new FMCommandDefaultParser();
        setDefaultCommand();
    }

    private void setDefaultCommand() {
        FMCommandInvoker.getInstance().put(new FMDefaultCommand());
    }

    private void setDefaultHandler() {
        this.fmMessageDeliveryHandler = new FMDefaultMessageDeliveryHandler();
        this.fmMessageHandler = new FMDefaultMessageHandler();
        this.fmMessagePostBackHandler = new FMDefaultMessagePostBackHandler();
        this.fmMessageReadHandler = new FMDefaultMessageReadHandler();
        this.fmMessageReferralHandler = new FMDefaultMessageReferralHandler();
    }

    private List<FMHandler> getHandlers() {
        List<FMHandler> handlers = new ArrayList<FMHandler>();
        if (this.fmMessageDeliveryHandler != null) {
            handlers.add(fmMessageDeliveryHandler);
        }
        if (this.fmMessageHandler != null) {
            handlers.add(fmMessageHandler);
        }
        if (this.fmMessagePostBackHandler != null) {
            handlers.add(fmMessagePostBackHandler);
        }
        if (this.fmMessageReadHandler != null) {
            handlers.add(fmMessageReadHandler);
        }
        if (this.fmMessageReferralHandler != null) {
            handlers.add(fmMessageReferralHandler);
        }
        return handlers;
    }

    public void dispatch(String payload) {
        System.out.println("FMClient => dispatch " + payload);

        FMReceiveMessage body = JSON.parseObject(payload, FMReceiveMessage.class);
        logger.info("FMClient => dispatch: body {}", JSON.toJSONString(body));
        for (FMReceiveMessage.Entry entry : body.getEntry()) {
            for (FMReceiveMessage.Messaging messaging : entry.getMessaging()) {
                for (FMHandler fmHandler : getHandlers()) {
                    if (fmHandler.canHandle(messaging)) {
//                        if (logger.isDebugEnabled()) {
                        System.out.println("dispatch to handler: " + fmHandler.getClass().getCanonicalName());
                        logger.info("dispatch to {} handler", fmHandler.getClass().getCanonicalName());
//                        }
                        fmHandler.handle(messaging);
                        continue;
                    }
                }
            }

        }
    }


    public FMResult sendMessage(FMReplyMessage message) {
        return FMProvider.sendMessage(message);
    }

    /**
     * @see #sendProfileSetting(FMProfileSettingMessage)
     */
    @Deprecated
    public FMResult sendSetting(FMSettingMessage message) {
        return FMProvider.sendSetting(message);
    }

    public FMResult sendProfileSetting(FMProfileSettingMessage message) {
        return FMProvider.sendProfileSetting(message);
    }

    public FMUser getUserProfile(String id) {
        return FMProvider.getUserProfile(id);
    }

    public boolean signature(String payload, String xHubSignature) {
        if (logger.isDebugEnabled()) {
            logger.debug("payload is : {}", payload);
            logger.debug("xHubSignature is : {}", xHubSignature);
        }
        if (this.accessSecret == null) {
            throw new AccessSecretUndefinedException();
        }

        String decodePayload = Signature.decode(payload, this.accessSecret);
        boolean signature = StringUtils.equals(decodePayload, xHubSignature);
        if (logger.isDebugEnabled()) {
            logger.debug("decode palyload signature is : {}, signature result is : {}", decodePayload, signature);
        }
        return signature;
    }

    public FMClient withFMCommands(FMCommand... fmCommands) {
        for (FMCommand fmCommand : fmCommands) {
            FMCommandInvoker.getInstance().put(fmCommand);
        }
        return this;
    }

    public FMClient withFmMessageDeliveryHandler(FMMessageDeliveryHandler fmMessageDeliveryHandler) {
        this.fmMessageDeliveryHandler = fmMessageDeliveryHandler;
        return this;
    }

    public FMClient withFmMessageHandler(FMMessageHandler fmMessageHandler) {
        this.fmMessageHandler = fmMessageHandler;
        return this;
    }

    public FMClient withFmMessagePostBackHandler(FMMessagePostBackHandler fmMessagePostBackHandler) {
        this.fmMessagePostBackHandler = fmMessagePostBackHandler;
        return this;
    }

    public FMClient withFmMessageReadHandler(FMMessageReadHandler fmMessageReadHandler) {
        this.fmMessageReadHandler = fmMessageReadHandler;
        return this;
    }

    public FMClient withFmMessageReferralHandler(FMMessageReferralHandler fmMessageReferralHandler) {
        this.fmMessageReferralHandler = fmMessageReferralHandler;
        return this;
    }

    public FMResultAspect getFmResultAspect() {
        return fmResultAspect;
    }

    public FMClient withFmResultAspect(FMResultAspect fmResultAspect) {
        this.fmResultAspect = fmResultAspect;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public FMClient withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public FMClient withAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    public FMCommandParser getFmCommandParser() {
        return this.fmCommandParser != null ? this.fmCommandParser : new FMCommandDefaultParser();
    }

    public FMClient withFMCommandParser(FMCommandParser fmCommandParser) {
        this.fmCommandParser = fmCommandParser;
        return this;
    }
}
