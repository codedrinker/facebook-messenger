package com.github.codedrinker.fm.builder;

import com.github.codedrinker.fm.entity.FMProfileSettingMessage;
import com.github.codedrinker.fm.entity.FMProfileSettingMessage.Greeting;
import com.github.codedrinker.fm.entity.FMProfileSettingMessage.PersistentMenu;

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

    public FMProfileSettingMessengeBuilder withGreeting(String... greets) {
        if (greets != null && greets.length > 0) {
            List<Greeting> greetingList = new ArrayList<Greeting>();
            for (String greet : greets) {
                Greeting greeting = new Greeting();
                greeting.setText(greet);
                greetingList.add(greeting);
            }
            this.profileSettingMessage.setGreeting(greetingList);
        }
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
        this.profileSettingMessage.setGet_started(payload);
        return this;
    }

    public FMProfileSettingMessengeBuilder withAccountLinkingUrl(String authUrl) {
        this.profileSettingMessage.setAccount_linking_url(authUrl);
        return this;
    }

    public FMProfileSettingMessengeBuilder withPersistentMenu(PersistentMenu menu) {
        this.profileSettingMessage.setPersistent_menu(menu);
        return this;
    }

    public FMProfileSettingMessage build() {
        return this.profileSettingMessage;
    }
}
