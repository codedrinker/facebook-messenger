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
package com.github.codedrinker.fm.parser;

/**
 * Command 解析器 通用接口, 通过消息中的有效载体，生成 Command 的解析器。
 */
public interface FMCommandParser {

    /**
     * @return Command Name
     */
    String getName();

    /**
     * @return Command Params
     */
    String[] getParams();

    /**
     * 解析payload 字段，与{@link #print(String, String...)} 的编码操作相对应;
     * <br/>
     * 根据 payload 生成 Command 的解析器
     *
     * @param payload 消息有效载体
     * @return 解析器
     */
    FMCommandParser parse(String payload);

    /**
     * 根据 特定的编码规则，将 Command 的 Name 和 Params 编码成 有效的消息载体
     * <br/>
     * <strong>注意:</strong> 使用 的编码规则和要解码规则配对，保证 通过{@link #parse(String)}解析方法能还原 Command Name 和 Command Params
     *
     * @param name   Command Name
     * @param params Command Params
     * @return 编码后的有效消息载体
     */
    String print(String name, String... params);
}
