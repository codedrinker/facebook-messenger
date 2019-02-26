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
 * 当打开 Messenger 机器人的m.me 链接中带了 ref 追踪参数，会发送 messaging_referrals Webhook Event
 * <br/>
 * 例如 http://m.me/<PAGE_NAME>?ref=<REF_PARAM>
 * <br/>
 *
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messaging_referrals"/>messaging_referrals webhook Event</a>
 */
public abstract class FMMessageReferralHandler implements FMHandler {
    /**
     * @param message {
     *                "sender":{
     *                "id":"<PSID>"
     *                },
     *                "recipient":{
     *                "id":"<PAGE_ID>"
     *                },
     *                "timestamp":1458692752478,
     *                "referral": {
     *                "ref": <REF_DATA_PASSED_IN_M.ME_PARAM>,
     *                "source": "SHORTLINK",
     *                "type": "OPEN_THREAD",
     *                }
     *                }
     * @return whether messaging_referrals webhook event
     */
    public boolean canHandle(FMReceiveMessage.Messaging message) {
        return message != null && message.getReferral() != null;
    }
}
