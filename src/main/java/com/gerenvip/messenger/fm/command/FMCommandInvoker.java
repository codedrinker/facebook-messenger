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
package com.gerenvip.messenger.fm.command;

import com.gerenvip.messenger.fm.FMClient;
import com.gerenvip.messenger.fm.command.builtin.FMDefaultCommand;
import com.gerenvip.messenger.fm.exception.DefaultCommandUndefinedException;
import com.gerenvip.messenger.fm.parser.FMCommandParser;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * invoke Command 对象
 */
@Slf4j
public class FMCommandInvoker {
    private static FMCommandInvoker fmCommandInvoker;
    private static Map<String, FMCommand> commands = new HashMap<String, FMCommand>();

    private FMCommandInvoker() {
    }

    public static FMCommandInvoker getInstance() {
        if (fmCommandInvoker == null) {
            fmCommandInvoker = new FMCommandInvoker();
        }
        return fmCommandInvoker;
    }

    /**
     * 动态增加 Command
     *
     * @param fmCommand {@link FMCommand} instance
     */
    public void put(FMCommand fmCommand) {
        commands.put(fmCommand.command(), fmCommand);
    }

    /**
     * 触发 默认的兜底 Command<br/>
     * 通过{@link FMClient#withFMCommands(FMCommand...)} 可以注入自定义的 Command，如果这些 Command 都不符合执行条件，会触发
     * Command 名称位 "DEFAULT"的Command，默认 是 {@link FMDefaultCommand},
     * 如果要重载 默认的Command，请使用{@link FMClient#setDefaultCommand(AbsDefaultCommand)}进行设置。
     *
     * @return FMCommand
     */
    private FMCommand invokeDefault() {
        if (commands.containsKey(AbsDefaultCommand.DEFAULT_COMMAND_NAME)) {
            return commands.get(AbsDefaultCommand.DEFAULT_COMMAND_NAME);
        } else {
            throw new DefaultCommandUndefinedException();
        }
    }

    /**
     * 根据 {@link FMCommandParser} 获取 正确的 Command 对象
     *
     * @param parse {@link FMCommandParser}
     * @return FMCommand
     */
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
