package com.github.codedrinker.fm.handler.change;

import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.FMSubscriptionChangeHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * 在 Facebook 应用后台 Webhook 配置时，如果订阅了 绑定的 Facebook Page。当 Facebook Page 的 一些配置字段发生变化时，就会触发 change 类型的 Webhook Event，
 * 例如有管理员修改了 Page 的 email ，current_location， description 等等。
 * <br/>
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
public abstract class FMSubscribedPageChangeHandler implements FMSubscriptionChangeHandler {

    public boolean canHandle(FMReceiveMessage.Change change) {
        return change != null && change.getField() != null;
    }

    public boolean canHandle(FMReceiveMessage.Change change, String object) {
        return StringUtils.equalsIgnoreCase("page", object) && canHandle(change);
    }
}
