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
package com.gerenvip.messenger.fm.handler.message;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.FMHandler;

/**
 * 当用户阅读了 发送给他(她)的Page 消息后，会发送 message_reads Webbook Event
 * <br/>
 *
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/message-reads"/>message_reads webhook Event</a>
 */
public abstract class FMMessageReadHandler implements FMHandler {
    /**
     * @param message {
     *                "sender":{
     *                "id":"<PSID>"
     *                },
     *                "recipient":{
     *                "id":"<PAGE_ID>"
     *                },
     *                "timestamp":1458668856463,
     *                "read":{
     *                "watermark":1458668856253,
     *                "seq":38
     *                }
     *                }
     * @return whether message_reads webhook event
     */
    public boolean canHandle(FMReceiveMessage.Messaging message) {
        return message != null && message.getRead() != null;
    }
}
