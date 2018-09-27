package com.github.codedrinker.fm.handler.change.builtin;

import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.change.FMSubscribedPageChangeHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
@Slf4j
public class FMDefaultPageChangeHandler extends FMSubscribedPageChangeHandler {

    public void handle(FMReceiveMessage.Change change) {
        log.info("Subscribed Page Changed -> {} ", change);
    }

}
