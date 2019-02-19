package com.github.codedrinker.fm.handler.change;

import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.FMSubscriptionChangeHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * 在 Facebook 应用后台 Webhook 配置时，如果订阅了 Permissions 的变化。当 一些 Permission 发生变化时 ，就会触发 change 类型的 Webhook Event，
 * 例如 我们获取用户的 location 的权限发生变化时。
 * <br/>
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
public abstract class FMSubscribedPermissionsChangeHandler implements FMSubscriptionChangeHandler {

    public boolean canHandle(FMReceiveMessage.Change change) {
        return change != null && change.getField() != null;
    }

    public boolean canHandle(FMReceiveMessage.Change change, String object) {
        return StringUtils.equalsIgnoreCase("permissions", object) && canHandle(change);
    }
}
