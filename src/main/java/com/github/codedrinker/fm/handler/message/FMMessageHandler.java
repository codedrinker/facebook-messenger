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
package com.github.codedrinker.fm.handler.message;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.handler.FMHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 当用户往绑定的Facebook Page上发送一消息，webhook 会收到 messages 类型的 webhook Event
 * <br/>
 * 消息可能是 一个文本消息，也可能是一个带附件的消息，比如 模板消息，图片，音频，视频，文件等等。
 *
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messages"/>messages webhook Event</a>
 */
@Slf4j
public abstract class FMMessageHandler implements FMHandler {
    /**
     * @param message {
     *                "sender":{
     *                "id":"<PSID>"
     *                },
     *                "recipient":{
     *                "id":"<PAGE_ID>"
     *                },
     *                "timestamp":1458692752478,
     *                "message":{
     *                "mid":"mid.1457764197618:41d102a3e1ae206a38",
     *                "text":"hello, world!",
     *                "quick_reply": {
     *                "payload": "<DEVELOPER_DEFINED_PAYLOAD>"
     *                }
     *                }
     *                }
     *                <br/>
     *                或者 带 attachment 的消息
     *                <br/>
     *                {
     *                "id": "682498302938465",
     *                "time": 1518479195594,
     *                "messaging": [
     *                {
     *                "sender": {
     *                "id": "<PSID>"
     *                },
     *                "recipient": {
     *                "id": "<PAGE_ID>"
     *                },
     *                "timestamp": 1518479195308,
     *                "message": {
     *                "mid": "mid.$cAAJdkrCd2ORnva8ErFhjGm0X_Q_c",
     *                "seq": 42181,
     *                "attachments": [
     *                {
     *                "type": "<image|video|audio|file>",
     *                "payload": {
     *                "url": "<ATTACHMENT_URL>"
     *                }
     *                }
     *                ]
     *                }
     *                }
     *                ]
     *                }
     * @return whether messages webhook event
     */
    public boolean canHandle(FMReceiveMessage.Messaging message) {
        return message != null && message.getMessage() != null;
    }

    /**
     * 检测是否是 Quick Reply 消息
     */
    protected boolean isQuickReply(FMReceiveMessage.Messaging messaging) {
        return canHandle(messaging) && messaging.getMessage().getQuick_reply() != null && StringUtils.isNoneBlank(messaging.getMessage().getQuick_reply().getPayload());
    }

    public final void handle(FMReceiveMessage.Messaging messaging) {
        if (isQuickReply(messaging)) {
            handleQuickReply(messaging, messaging.getMessage().getQuick_reply());
        } else {
            FMReceiveMessage.Messaging.Message message = messaging.getMessage();
            if (BooleanUtils.isTrue(message.getIs_echo())) {
                log.info("FMMessageHandler -> handle() is echo, messaging -> {}", JSON.toJSONString(messaging));
            } else {
                handleMessage(messaging);
            }
        }
    }

    /**
     * 处理 非 {@link com.github.codedrinker.fm.entity.FMReplyMessage.QuickReply} 类型的其他消息
     */
    protected abstract void handleMessage(FMReceiveMessage.Messaging messaging);

    /**
     * 处理 QuickReply 消息
     *
     * @param messaging  {@link FMReceiveMessage.Messaging}
     * @param quickReply {@link FMReceiveMessage.Messaging.Message.QuickReply}
     */
    protected abstract void handleQuickReply(FMReceiveMessage.Messaging messaging, FMReceiveMessage.Messaging.Message.QuickReply quickReply);

}