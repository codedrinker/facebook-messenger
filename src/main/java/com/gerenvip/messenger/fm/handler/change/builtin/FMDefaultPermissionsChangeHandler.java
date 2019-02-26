package com.gerenvip.messenger.fm.handler.change.builtin;

import com.gerenvip.messenger.fm.entity.FMReceiveMessage;
import com.gerenvip.messenger.fm.handler.change.FMSubscribedPermissionsChangeHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认处理 订阅的 Permissions 变化信息
 *
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
@Slf4j
public class FMDefaultPermissionsChangeHandler extends FMSubscribedPermissionsChangeHandler {

    public void handle(FMReceiveMessage.Change change) {
        log.info("Subscribed Permissions Changed -> {} ", change);
    }

}
