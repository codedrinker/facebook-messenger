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
 * 用户触发 “回传"按钮，“马上开始"按钮，或者固定菜单时，Messenger 平台会发送 messaging_postbacks 类型的 webhook Event
 * <br/>
 *
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messaging_postbacks"/>messaging_postbacks webhook Event</a>
 */
public abstract class FMMessagePostBackHandler implements FMHandler {

    /**
     * @param message {
     *                "sender":{
     *                "id":"<PSID>"
     *                },
     *                "recipient":{
     *                "id":"<PAGE_ID>"
     *                },
     *                "timestamp":1458692752478,
     *                "postback":{
     *                "title": "<TITLE_FOR_THE_CTA>",
     *                "payload": "<USER_DEFINED_PAYLOAD>",
     *                "referral": {
     *                "ref": "<USER_DEFINED_REFERRAL_PARAM>",
     *                "source": "<SHORTLINK>",
     *                "type": "OPEN_THREAD",
     *                }
     *                }
     *                }
     * @return whether messaging_postbacks webhook event
     */
    public boolean canHandle(FMReceiveMessage.Messaging message) {
        return message != null && message.getPostback() != null;
    }
}
