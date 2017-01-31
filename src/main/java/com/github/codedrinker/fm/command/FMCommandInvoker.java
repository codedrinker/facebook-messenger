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
package com.github.codedrinker.fm.command;

import com.github.codedrinker.fm.exception.DefaultCommandUndefinedException;
import com.github.codedrinker.fm.parser.FMCommandParser;

import java.util.HashMap;
import java.util.Map;

public class FMCommandInvoker {
    private static FMCommandInvoker fmCommandInvoker;
    public static Map<String, FMCommand> commands;

    private FMCommandInvoker() {
    }

    public static FMCommandInvoker getInstance() {
        if (fmCommandInvoker == null) {
            fmCommandInvoker = new FMCommandInvoker();
            commands = new HashMap<>();
        }
        return fmCommandInvoker;
    }

    public void put(FMCommand fmCommand) {
        commands.put(fmCommand.command(), fmCommand);
    }

    private FMCommand invokeDefault() {
        if (commands.containsKey("DEFAULT")) {
            return commands.get("DEFAULT");
        } else {
            throw new DefaultCommandUndefinedException();
        }
    }

    public FMCommand invoke(FMCommandParser parse) {
        try {
            if (parse != null && parse.getName() != null) {
                if (commands.containsKey(parse.getName())) {
                    return commands.get(parse.getName());
                } else {
                    return invokeDefault();
                }
            } else {
                return invokeDefault();
            }
        } catch (Exception e) {
            return invokeDefault();
        }
    }
}
