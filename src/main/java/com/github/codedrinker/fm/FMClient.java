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
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.exception.AccessSecretUndefinedException;
import com.github.codedrinker.fm.handler.*;
import com.github.codedrinker.fm.parser.FMCommandDefaultParser;
import com.github.codedrinker.fm.parser.FMCommandParser;
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
    private List<FMHandler> handlers = new ArrayList<FMHandler>();

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
        this.handlers.add(new FMDefaultMessageHandler());
        this.handlers.add(new FMDefaultMessageDeliveryHandler());
        this.handlers.add(new FMDefaultMessagePostBackHandler());
        this.handlers.add(new FMDefaultMessageReadHandler());
        this.handlers.add(new FMDefaultMessageReferralHandler());
    }

    public void dispatch(String payload) {
        FMReceiveMessage body = JSON.parseObject(payload, FMReceiveMessage.class);
        for (FMReceiveMessage.Entry entry : body.getEntry()) {
            for (FMReceiveMessage.Messaging messaging : entry.getMessaging()) {
                for (FMHandler fmHandler : this.handlers) {
                    if (fmHandler.canHandle(messaging)) {
                        fmHandler.handle(messaging);
                        continue;
                    }
                }
            }

        }
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

    public void withHandlers(FMHandler... handlers) {
        for (FMHandler fmHandler : handlers) {
            this.handlers.add(1, fmHandler);
        }
    }

    public void withFMCommands(FMCommand... fmCommands) {
        for (FMCommand fmCommand : fmCommands) {
            FMCommandInvoker.getInstance().put(fmCommand);
        }
    }

    public FMResultAspect getFmResultAspect() {
        return fmResultAspect;
    }

    public void setFmResultAspect(FMResultAspect fmResultAspect) {
        this.fmResultAspect = fmResultAspect;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public FMCommandParser getFmCommandParser() {
        return this.fmCommandParser != null ? this.fmCommandParser : new FMCommandDefaultParser();
    }

    public void setFMCommandParser(FMCommandParser fmCommandParser) {
        this.fmCommandParser = fmCommandParser;
    }
}
