package com.github.codedrinker.fm.handler.change;

import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.FMSubscriptionChangeHandler;
import org.apache.commons.lang3.StringUtils;

/**
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
