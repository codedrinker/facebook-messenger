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

public interface FMCommand {
    /**
     * 返回一个带参数的 Command 有效负载。
     * 一般会调用 {@link com.github.codedrinker.fm.parser.FMCommandParser#toCommand(String, String...)}
     * 方法 ，通过编码生成一个 payload 字符串
     *
     * @param params Command 参数
     * @return payload 字符串
     */
    String command(String... params);

    /**
     * 执行 该 Command
     *
     * @param recipient 消息接收者Id，就是和 Bold 对话的 用户Id
     * @param params    有效负载中携带的 参数信息
     */
    void execute(String recipient, String... params);
}
