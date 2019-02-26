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
package com.gerenvip.messenger.fm.command.builtin;

import com.gerenvip.messenger.fm.builder.FMReplyMessageBuilder;
import com.gerenvip.messenger.fm.command.AbsDefaultCommand;
import com.gerenvip.messenger.fm.provider.FMProvider;

public class FMDefaultCommand extends AbsDefaultCommand {

    public void execute(String recipient, String... params) {
        FMProvider.sendMessage(FMReplyMessageBuilder
                .textBuilder(recipient, "Sorry, try again? Use a few words to tell me what you gonna to know more about.")
                .build());
    }
}
