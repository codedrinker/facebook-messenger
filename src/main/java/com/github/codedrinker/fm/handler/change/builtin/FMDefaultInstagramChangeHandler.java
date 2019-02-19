package com.github.codedrinker.fm.handler.change.builtin;

import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.handler.change.FMSubscribedInstagramChangeHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认 处理 Ins 相关的 订阅变化
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
@Slf4j
public class FMDefaultInstagramChangeHandler extends FMSubscribedInstagramChangeHandler {

    public void handle(FMReceiveMessage.Change change) {
        log.info("Subscribed Instagram Changed -> {} ", change);
    }

}
