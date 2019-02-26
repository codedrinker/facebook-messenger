package com.gerenvip.messenger.fm.handler.change.builtin;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.change.FMSubscribedUserChangeHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认处理 User 的信息变化
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
@Slf4j
public class FMDefaultUserChangeHandler extends FMSubscribedUserChangeHandler {

    public void handle(FMReceiveMessage.Change change) {
        log.info("Subscribed User Changed -> {} ", change);
    }

}
