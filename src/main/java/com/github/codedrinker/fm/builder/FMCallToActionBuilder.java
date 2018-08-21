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

import com.github.codedrinker.fm.entity.FMEnum;
import com.github.codedrinker.fm.entity.FMSettingMessage;

import java.util.ArrayList;

public class FMCallToActionBuilder {

    private FMSettingMessage.CallToAction callToAction;

    public FMCallToActionBuilder() {
        this.callToAction = new FMSettingMessage.CallToAction();
    }

    public static FMCallToActionBuilder defaultBuilder() {
        return new FMCallToActionBuilder();
    }

    public FMCallToActionBuilder withPayload(String payload) {
        this.callToAction.setPayload(payload);
        return this;
    }

    public FMCallToActionBuilder withWebViewHeightRatio(FMEnum.WebViewHeightRatio webViewHeightRatio) {
        this.callToAction.setWebview_height_ratio(webViewHeightRatio);
        return this;
    }

    public FMCallToActionBuilder withUrl(String url) {
        this.callToAction.setUrl(url);
        return this;
    }

    public FMCallToActionBuilder withTitle(String title) {
        this.callToAction.setTitle(title);
        return this;
    }

    public FMCallToActionBuilder withType(FMSettingMessage.CallActionType callActionType) {
        this.callToAction.setType(callActionType);
        return this;
    }

    /**
     * 设置 嵌套 callToActions 用着 嵌套menu
     */
    public FMCallToActionBuilder withCallToAction(FMSettingMessage.CallToAction... callToActions) {
        if (callToActions.length != 0) {
            if (this.callToAction.getCall_to_actions() == null) {
                this.callToAction.setCall_to_actions(new ArrayList<FMSettingMessage.CallToAction>());
            }
            for (FMSettingMessage.CallToAction callToAction : callToActions) {
                this.callToAction.getCall_to_actions().add(callToAction);
            }
        }
        return this;
    }

    public FMSettingMessage.CallToAction build() {
        return this.callToAction;
    }
}
