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
package com.github.codedrinker.fm.command;

import com.github.codedrinker.fm.FMClient;
import com.github.codedrinker.fm.builder.FMReplyMessageBuilder;
import com.github.codedrinker.fm.provider.FMProvider;

public class FMDefaultCommand implements FMCommand {

    public String command(String... params) {
        return FMClient.getInstance().getFmCommandParser().print("DEFAULT", params);
    }

    public void execute(String recipient, String... params) {
        FMProvider.sendMessage(FMReplyMessageBuilder
                .textBuilder(recipient, "Sorry, try again? Use a few words to tell me what you gonna to know more about.")
                .build());
    }
}
