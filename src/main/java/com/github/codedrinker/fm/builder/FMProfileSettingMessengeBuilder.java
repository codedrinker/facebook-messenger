package com.github.codedrinker.fm.builder;

import com.github.codedrinker.fm.entity.FMProfileSettingMessage;
import com.github.codedrinker.fm.entity.FMProfileSettingMessage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author wangwei on 2018/8/22.
 * wangwei@jiandaola.com
 */
public class FMProfileSettingMessengeBuilder {

    private FMProfileSettingMessage profileSettingMessage;

    public FMProfileSettingMessengeBuilder() {
        this.profileSettingMessage = new FMProfileSettingMessage();
    }

    public static FMProfileSettingMessengeBuilder defaultBuilder() {
        return new FMProfileSettingMessengeBuilder();
    }

    /**
     * 如果设置多个，请保证 locale 不一样
     */
    public FMProfileSettingMessengeBuilder withGreeting(String greet) {
        List<Greeting> greetingList = new ArrayList<Greeting>();
        Greeting greeting = new Greeting();
        greeting.setText(greet);
        greetingList.add(greeting);
        this.profileSettingMessage.setGreeting(greetingList);
        return this;
    }

    public FMProfileSettingMessengeBuilder withGreeting(Greeting... greetings) {
        if (greetings != null && greetings.length > 0) {
            List<Greeting> list = new ArrayList<Greeting>(Arrays.asList(greetings));
            if (profileSettingMessage.getGreeting() == null) {
                this.profileSettingMessage.setGreeting(list);
            } else {
                this.profileSettingMessage.getGreeting().addAll(list);
            }
        }
        return this;
    }

    /**
     * 用户轻触“开始”按钮后，在 messaging_postbacks 事件中发送给您的 Webhook 的负载
     */
    public FMProfileSettingMessengeBuilder withGetStartedPayload(String payload) {
        Payload payloadObj = new Payload();
        payloadObj.setPayload(payload);
        this.profileSettingMessage.setGet_started(payloadObj);
        return this;
    }

    public FMProfileSettingMessengeBuilder withAccountLinkingUrl(String authUrl) {
        this.profileSettingMessage.setAccount_linking_url(authUrl);
        return this;
    }

    public FMProfileSettingMessengeBuilder withPersistentMenu(PersistentMenu... persistentMenu) {
        if (persistentMenu != null && persistentMenu.length > 0) {
            List<PersistentMenu> list = new ArrayList<PersistentMenu>(Arrays.asList(persistentMenu));
            if (profileSettingMessage.getPersistent_menu() == null) {
                this.profileSettingMessage.setPersistent_menu(list);
            } else {
                this.profileSettingMessage.getPersistent_menu().addAll(list);
            }
        }
        return this;
    }

    public FMProfileSettingMessage build() {
        return this.profileSettingMessage;
    }
}
