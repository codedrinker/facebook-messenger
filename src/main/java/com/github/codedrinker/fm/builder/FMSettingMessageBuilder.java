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
package com.github.codedrinker.fm.builder;

import com.github.codedrinker.fm.entity.FMSettingMessage;

import java.util.ArrayList;

/**
 * @see com.github.codedrinker.fm.entity.FMProfileSettingMessage
 */
@Deprecated
public class FMSettingMessageBuilder {


    private FMSettingMessage fmSettingMessage;

    public FMSettingMessageBuilder() {
        this.fmSettingMessage = new FMSettingMessage();
    }

    public static FMSettingMessageBuilder defaultBuilder() {
        return new FMSettingMessageBuilder();
    }

    public FMSettingMessageBuilder withGreeting(String text) {
        FMSettingMessage.Greeting greeting = new FMSettingMessage.Greeting();
        greeting.setText(text);
        this.fmSettingMessage.setGreeting(greeting);
        return this;
    }

    public FMSettingMessageBuilder withSettingType(FMSettingMessage.SettingType settingType) {
        this.fmSettingMessage.setSetting_type(settingType);
        return this;
    }

    public FMSettingMessageBuilder withThreadState(FMSettingMessage.ThreadState threadState) {
        this.fmSettingMessage.setThread_state(threadState);
        return this;
    }

    public FMSettingMessageBuilder withCallToAction(FMSettingMessage.CallToAction... callToActions) {
        if (callToActions.length != 0) {
            if (this.fmSettingMessage.getCall_to_actions() == null) {
                this.fmSettingMessage.setCall_to_actions(new ArrayList<FMSettingMessage.CallToAction>());
            }
            for (FMSettingMessage.CallToAction callToAction : callToActions) {
                this.fmSettingMessage.getCall_to_actions().add(callToAction);
            }
        }
        return this;
    }

    public FMSettingMessage build() {
        return this.fmSettingMessage;
    }
}
