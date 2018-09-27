package com.github.codedrinker.fm.handler;

import com.github.codedrinker.fm.entity.FMReceiveMessage;

/**
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
public interface FMSubscriptionChangeHandler extends IHandler<FMReceiveMessage.Change> {

    boolean canHandle(FMReceiveMessage.Change change, String object);
}
