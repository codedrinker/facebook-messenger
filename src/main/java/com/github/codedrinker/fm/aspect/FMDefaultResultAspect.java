/*
 * Copyright 2017 Chunlei Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.codedrinker.fm.aspect;

import com.github.codedrinker.fm.entity.FMResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMDefaultResultAspect implements FMResultAspect {
    Logger logger = LoggerFactory.getLogger(FMDefaultResultAspect.class);

    public void handle(FMResult fmpResult) {
        if (fmpResult != null && fmpResult.getError() != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Request Facebook Message API occur an error. {}", fmpResult.getError());
            }
        }
    }
}
