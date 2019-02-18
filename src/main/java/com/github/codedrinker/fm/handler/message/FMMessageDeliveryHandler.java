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

import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.FMHandler;

/**
 * 处理消息送达后的回调。当消息送达后，Messenger平台会回调 message_deliveries 事件，该 类型用于处理该回调事件
 * <p>
 * <br/>
 * 事件类型参考: <br/>
 * https://developers.facebook.com/docs/messenger-platform/webhook#setup
 * <br/>
 */
public abstract class FMMessageDeliveryHandler implements FMHandler {
    /**
     * @param message {
     *                "sender":{
     *                "id":"<PSID>"
     *                },
     *                "recipient":{
     *                "id":"<PAGE_ID>"
     *                },
     *                "delivery":{
     *                "mids":[
     *                "mid.1458668856218:ed81099e15d3f4f233"
     *                ],
     *                "watermark":1458668856253,
     *                "seq":37
     *                }
     *                }
     * @return whether delivery webhook event
     */
    public boolean canHandle(FMReceiveMessage.Messaging message) {
        return message != null && message.getDelivery() != null;
    }
}
