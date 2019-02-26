package com.fm.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.gerenvip.messenger.fm.aspect.FMResultAspect;
import com.gerenvip.messenger.fm.entity.FMResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangwei on 2019/2/23.
 * wangwei@jiandaola.com
 */
@Slf4j
@Component
public class ResultAspect implements FMResultAspect {
    @Override
    public void handle(FMResult fmpResult) {
        log.info("FMPAspect => {}", JSON.toJSONString(fmpResult));
    }
}
