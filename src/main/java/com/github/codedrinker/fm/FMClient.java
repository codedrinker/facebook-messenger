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
import com.github.codedrinker.fm.command.AbsDefaultCommand;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.command.FMCommandInvoker;
import com.github.codedrinker.fm.command.builtin.FMDefaultCommand;
import com.github.codedrinker.fm.entity.*;
import com.github.codedrinker.fm.exception.AccessSecretUndefinedException;
import com.github.codedrinker.fm.handler.*;
import com.github.codedrinker.fm.handler.message.*;
import com.github.codedrinker.fm.handler.message.builtin.*;
import com.github.codedrinker.fm.parser.builtin.FMCommandDefaultParser;
import com.github.codedrinker.fm.parser.FMCommandParser;
import com.github.codedrinker.fm.provider.FMProvider;
import com.github.codedrinker.fm.utils.Signature;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FMClient {

    private FMCommandParser fmCommandParser;
    private String accessToken;
    private String accessSecret;
    private FMResultAspect fmResultAspect;

    private FMMessageDeliveryHandler fmMessageDeliveryHandler;
    private FMMessageHandler fmMessageHandler;
    private FMMessagePostBackHandler fmMessagePostBackHandler;
    private FMMessageReadHandler fmMessageReadHandler;
    private FMMessageReferralHandler fmMessageReferralHandler;

    private List<FMSubscriptionChangeHandler> subscriptionChangeHandlers;

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

    /**
     * check config state
     */
    private void checkConfigured() {
        if (StringUtils.isEmpty(accessSecret) || StringUtils.isEmpty(accessToken)) {
            throw new IllegalStateException("you must call config(accessSecret,accessToken) first");
        }
    }

    /**
     * 配置 FMClient
     *
     * @param accessSecret accessSecret 在 Facebook 开发者后台的 对应 App 设置中 查看 应用密钥
     * @param accessToken  在 Facebook 开发者后台 获取 Facebook Page 的 accessToken
     * @return FMClient
     */
    public FMClient config(@NotNull String accessSecret, String accessToken) {
        this.accessSecret = accessSecret;
        this.accessToken = accessToken;
        return this;
    }

    /**
     * set default Command
     *
     * @param defaultCommand
     */
    public void setDefaultCommand(@Nullable AbsDefaultCommand defaultCommand) {
        FMCommandInvoker.getInstance().put(defaultCommand == null ? new FMDefaultCommand() : defaultCommand);
    }

    private void setDefaultHandler() {
        this.fmMessageDeliveryHandler = new FMDefaultMessageDeliveryHandler();
        this.fmMessageHandler = new FMDefaultMessageHandler();
        this.fmMessagePostBackHandler = new FMDefaultMessagePostBackHandler();
        this.fmMessageReadHandler = new FMDefaultMessageReadHandler();
        this.fmMessageReferralHandler = new FMDefaultMessageReferralHandler();
    }

    private List<FMHandler> getMessagingHandlers() {
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

    private List<FMSubscriptionChangeHandler> getSubscriptionChangeHandlers() {
        return subscriptionChangeHandlers;
    }

    /**
     * 核心分发 payload，找到对应的 {@link FMHandler} 来处理消息
     *
     * @param payload 有效消息负载
     */
    public void dispatch(String payload) {
        log.info("FMClient => dispatch payload:" + payload);
        FMReceiveMessage body = JSON.parseObject(payload, FMReceiveMessage.class);
        log.info("FMClient => dispatch: convert to FMReceiveMessage: {}", JSON.toJSONString(body));
        if (body == null) {
            log.info("FMClient => dispatch，  Empty or Illegal payload");
            return;
        }
        String object = body.getObject();
        for (FMReceiveMessage.Entry entry : body.getEntry()) {
            if (entry.getMessaging() != null) {
                handleMessaging(entry.getMessaging());
            } else if (entry.getChanges() != null) {
                handleChange(entry.getChanges(), object);
            } else if (entry.getChanged_fields() != null) {
                handleChangeFiled(entry.getChanged_fields(), object);
            } else {
                log.info("Unsupport entry -> {}", JSON.toJSONString(entry));
            }
        }
    }

    private void handleChange(List<FMReceiveMessage.Change> changes, String object) {
        if (changes == null) {
            return;
        }
        for (FMReceiveMessage.Change change : changes) {
            List<FMSubscriptionChangeHandler> changeHandlers = getSubscriptionChangeHandlers();
            if (changeHandlers != null) {
                for (FMSubscriptionChangeHandler handler : changeHandlers) {
                    if (handler.canHandle(change, object)) {
                        log.debug("handleChange find handler {} ", handler.getClass().getCanonicalName());
                        handler.handle(change);
                    }
                }
            }
        }
    }

    private void handleChangeFiled(List<String> changeFileds, String object) {
        if (changeFileds == null) {
            return;
        }
        for (String changeFiled : changeFileds) {
            List<FMSubscriptionChangeHandler> changeHandlers = getSubscriptionChangeHandlers();
            if (changeHandlers != null) {
                for (FMSubscriptionChangeHandler handler : changeHandlers) {
                    FMReceiveMessage.Change change = FMReceiveMessage.Change.emptyValue(changeFiled);
                    if (handler.canHandle(change, object)) {
                        log.debug("handleChangeFiled find handler {} ", handler.getClass().getCanonicalName());
                        handler.handle(change);
                    }
                }
            }
        }
    }

    private void handleMessaging(List<FMReceiveMessage.Messaging> messagings) {
        if (messagings == null) {
            return;
        }
        for (FMReceiveMessage.Messaging messaging : messagings) {
            for (FMHandler fmHandler : getMessagingHandlers()) {
                if (fmHandler.canHandle(messaging)) {
                    log.debug("handleMessaging find handler {} ", fmHandler.getClass().getCanonicalName());
                    fmHandler.handle(messaging);
                }
            }
        }
    }

    public FMResult sendMessage(FMReplyMessage message) {
        return FMProvider.sendMessage(message);
    }

    /**
     * 初始化配置 Messenger Bold，例如配置欢迎页，固定菜单 等等
     *
     * @param message {@link FMProfileSettingMessage}
     * @return {@link FMResult}
     */
    public FMResult sendProfileSetting(FMProfileSettingMessage message) {
        return FMProvider.sendProfileSetting(message);
    }

    /**
     * 获取用户的Profile 信息
     *
     * @param id 用户的 Messenger Id
     * @return
     */
    public RawFMUser getUserProfile(String id) {
        return FMProvider.getUserProfile(id);
    }

    /**
     * 签名校验 Messenger Platform 发送的数据 是否合法
     *
     * @param payload       Messenger Plaform 发送来的数据
     * @param xHubSignature 签名摘要，Webhook 被执行的 request 请求Header 中会包含 key 为 "X-Hub-Signature" 的摘要信息，用于执行校验消息的合法性
     * @return true :消息合法，来自于 Messenger Platform; false: 消息不合法，可能是 accessSecret 密钥错误，也可能消息被篡改
     */
    public boolean signature(String payload, String xHubSignature) {
        log.debug("payload is : {}", payload);
        log.debug("xHubSignature is : {}", xHubSignature);
        if (this.accessSecret == null) {
            throw new AccessSecretUndefinedException();
        }

        String decodePayload = Signature.decode(payload, this.accessSecret);
        boolean signature = StringUtils.equals(decodePayload, xHubSignature);
        log.debug("decode palyload signature is : {}, signature result is : {}", decodePayload, signature);
        return signature;
    }

    /**
     * 注入 支持的 Command
     *
     * @param fmCommands
     * @return FMClient
     */
    public FMClient withFMCommands(FMCommand... fmCommands) {
        for (FMCommand fmCommand : fmCommands) {
            FMCommandInvoker.getInstance().put(fmCommand);
        }
        return this;
    }

    /**
     * 用于处理 "message_deliveries"类型的 Webhook Event。
     * <br/>
     * 消息送达后 Messenger 平台将会产生一个 送达的 事件
     *
     * @param fmMessageDeliveryHandler {@link FMMessageDeliveryHandler}
     * @return FMClient
     */
    public FMClient withFmMessageDeliveryHandler(FMMessageDeliveryHandler fmMessageDeliveryHandler) {
        this.fmMessageDeliveryHandler = fmMessageDeliveryHandler;
        return this;
    }

    /**
     * 用于处理 “messages” 类型的 Webhook Event。
     * <br/>
     * 当用户给 Messenger 机器人绑定的 Facebook Page上发送了 一个消息，会触发该 Webhook Event，并携带消息体，
     * 消息可能是 文本消息，也可能是一个带附件的消息，比如 模板消息，图片，音频，视频，文件等等。
     *
     * @param fmMessageHandler {@link FMMessageHandler}
     * @return FMClient
     */
    public FMClient withFmMessageHandler(FMMessageHandler fmMessageHandler) {
        this.fmMessageHandler = fmMessageHandler;
        return this;
    }

    /**
     * 用于处理 “messaging_postbacks” 类型的 Webhook Event。
     *
     * @param fmMessagePostBackHandler {@link FMMessagePostBackHandler}
     * @return FMClient
     */
    public FMClient withFmMessagePostBackHandler(FMMessagePostBackHandler fmMessagePostBackHandler) {
        this.fmMessagePostBackHandler = fmMessagePostBackHandler;
        return this;
    }

    /**
     * 用于处理 “message_reads” 类型的 Webhook Event。
     *
     * @param fmMessageReadHandler {@link FMMessageReadHandler}
     * @return FMClient
     */
    public FMClient withFmMessageReadHandler(FMMessageReadHandler fmMessageReadHandler) {
        this.fmMessageReadHandler = fmMessageReadHandler;
        return this;
    }

    /**
     * 用于处理 “messaging_referrals” 类型的 Webhook Event。
     *
     * @param fmMessageReferralHandler {@link FMMessageReferralHandler}
     * @return FMClient
     */
    public FMClient withFmMessageReferralHandler(FMMessageReferralHandler fmMessageReferralHandler) {
        this.fmMessageReferralHandler = fmMessageReferralHandler;
        return this;
    }

    /**
     * 用于处理 Messenger Bold 主动订阅的 一些域的字段的变化情况。<br/>
     * 例如:<br/>
     * 在 Facebook 应用开发后台上配置了 Webhook ，并订阅了 Facebook Page 的email字段。当有管理员修改了Facebook Page 上的 email 后，
     * Messenger Bold 就会收到变化信息；而该方法就是来处理这个事件的。
     *
     * @param handler {@link FMSubscriptionChangeHandler}
     * @return FMClient
     */
    public FMClient withSubscribeChangeHandler(FMSubscriptionChangeHandler handler) {
        if (subscriptionChangeHandlers == null) {
            subscriptionChangeHandlers = new ArrayList<FMSubscriptionChangeHandler>();
        }
        if (!subscriptionChangeHandlers.contains(handler)) {
            subscriptionChangeHandlers.add(handler);
        }
        return this;
    }

    public FMResultAspect getFmResultAspect() {
        return fmResultAspect;
    }

    /**
     * 用于处理 所有与 Messenger Platform API 交互的响应结果。
     *
     * @param fmResultAspect 与 Messenger Platform API 交互的响应结果
     * @return FMClient
     */
    public FMClient withFmResultAspect(FMResultAspect fmResultAspect) {
        this.fmResultAspect = fmResultAspect;
        return this;
    }

    public String getAccessToken() {
        checkConfigured();
        return accessToken;
    }

    /**
     * 配置 accessToken
     *
     * @param accessToken 在 Facebook 开发者后台 获取 Facebook Page 的 accessToken
     * @return FMClient
     * @see #config(String, String)
     * @deprecated
     */
    @Deprecated
    public FMClient withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * 获取已经配置的 AccessSecret
     */
    public String getAccessSecret() {
        checkConfigured();
        return accessSecret;
    }

    /**
     * 配置 accessSecret
     *
     * @param accessSecret 在 Facebook 开发者后台的 对应 App 设置中 查看 应用密钥
     * @return FMClient
     * @see #config(String, String)
     * @deprecated
     */
    @Deprecated
    public FMClient withAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    /**
     * 获取配置的默认 Command 解析器
     *
     * @return {@link FMCommandParser}
     */
    public FMCommandParser getFmCommandParser() {
        return this.fmCommandParser != null ? this.fmCommandParser : FMCommandDefaultParser.getDefault();
    }

    /**
     * 注册 默认的 Command 解析器
     *
     * @param fmCommandParser {@link FMCommandParser}
     * @return FMClient
     */
    public FMClient registerFMCommandParser(FMCommandParser fmCommandParser) {
        this.fmCommandParser = fmCommandParser;
        return this;
    }
}
