package com.gerenvip.messenger.fm.handler.change;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.FMSubscriptionChangeHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认处理 订阅的 User 信息，当 User 的 一些 字段配置发生变化就会触发 change 类型的 Webhook Event，
 * 例如 订阅用户的个人简介 发生变化
 * <br/>
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
public abstract class FMSubscribedUserChangeHandler implements FMSubscriptionChangeHandler {

    public boolean canHandle(FMReceiveMessage.Change change) {
        return change != null && change.getField() != null;
    }

    public boolean canHandle(FMReceiveMessage.Change change, String object) {
        return StringUtils.equalsIgnoreCase("user", object) && canHandle(change);
    }
}
