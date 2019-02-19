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
package com.github.codedrinker.fm.handler.message.builtin;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.handler.message.FMMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class FMDefaultMessageHandler extends FMMessageHandler {

    /**
     * 处理 非 {@link FMReplyMessage.QuickReply} 类型的其他消息
     *
     * @param messaging
     */
    protected void handleMessage(FMReceiveMessage.Messaging messaging) {
        if (log.isDebugEnabled()) {
            log.debug("dispatch into default message handler, which is echo message : {}", JSON.toJSONString(messaging));
        }
    }

    /**
     * 处理 QuickReply 消息
     *
     * @param messaging  {@link FMReceiveMessage.Messaging}
     * @param quickReply {@link FMReceiveMessage.Messaging.Message.QuickReply}
     */
    protected void handleQuickReply(FMReceiveMessage.Messaging messaging, FMReceiveMessage.Messaging.Message.QuickReply quickReply) {
        if (log.isDebugEnabled()) {
            log.debug("dispatch into default message handler, which is quick reply message : {}", JSON.toJSONString(messaging));
        }
    }
}
