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
package com.github.codedrinker.fm.handler;

import com.github.codedrinker.fm.FMClient;
import com.github.codedrinker.fm.command.FMCommand;
import com.github.codedrinker.fm.command.FMCommandInvoker;
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.parser.FMCommandParser;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class FMDefaultMessageHandler extends FMMessageHandler {
    @Override
    public void handle(FMReceiveMessage.Messaging message) {
        if (message.getMessage().getQuick_reply() != null && StringUtils.isNotBlank(message.getMessage().getQuick_reply().getPayload())) {
            FMCommandParser parse = FMClient.getInstance().getFmCommandParser().parse(message.getMessage().getQuick_reply().getPayload());
            FMCommand command = FMCommandInvoker.getInstance().invoke(parse);
            command.execute(message.getSender().getId(), parse.getParams());
        } else {
            if (!BooleanUtils.isTrue(message.getMessage().getIs_echo())) {
                //basic message
            } else {
                //echo
            }
        }
    }
}
