
package com.gerenvip.messenger.fm.aspect;

import com.gerenvip.messenger.fm.entity.FMResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FMDefaultResultAspect implements FMResultAspect {

    public void handle(FMResult fmpResult) {
        if (fmpResult != null && fmpResult.getError() != null) {
            //用于辅助排除错误
            log.error("Request Facebook Message API occur an error. {}", fmpResult.getError());
        }
    }
}
