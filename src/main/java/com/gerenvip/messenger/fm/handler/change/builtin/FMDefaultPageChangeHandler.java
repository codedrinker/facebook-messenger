package com.gerenvip.messenger.fm.handler.change.builtin;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.change.FMSubscribedPageChangeHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认处理 订阅的 Page 相关的 变化
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
@Slf4j
public class FMDefaultPageChangeHandler extends FMSubscribedPageChangeHandler {

    public void handle(FMReceiveMessage.Change change) {
        log.info("Subscribed Page Changed -> {} ", change);
    }

}
