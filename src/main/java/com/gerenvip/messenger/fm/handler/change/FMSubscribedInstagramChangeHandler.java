package com.gerenvip.messenger.fm.handler.change;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.FMSubscriptionChangeHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * 在 Facebook 应用后台 Webhook 配置时，如果订阅了 绑定的 Instagram 的变化。当 Instagram 的 一些事件发生时，就会触发 change 类型的 Webhook Event
 * ，例如 用户评论了 我们绑定的 Ins。
 * <br/>
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
public abstract class FMSubscribedInstagramChangeHandler implements FMSubscriptionChangeHandler {

    public boolean canHandle(FMReceiveMessage.Change change) {
        return change != null && change.getField() != null;
    }

    public boolean canHandle(FMReceiveMessage.Change change, String object) {
        return StringUtils.equalsIgnoreCase("instagram", object) && canHandle(change);
    }
}
