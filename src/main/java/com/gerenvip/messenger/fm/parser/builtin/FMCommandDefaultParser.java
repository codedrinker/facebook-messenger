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
package com.gerenvip.messenger.fm.parser.builtin;

import com.gerenvip.messenger.fm.parser.FMCommandParser;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 默认的 Command 解析器
 * <br/>
 * 默认的 编码规则是 将 Command Name 与 Params 通过 "_"分割符号拼接成字符串。
 * 不过默认的解析器编码规则有一个确定就是要求 Command Name 中不能包含 "_"
 * <br/>
 * <br/>
 * 如果要获得更好的兼容效果，您可以 实现{@link FMCommandParser} 自定义实现 一个解析器
 */
public class FMCommandDefaultParser implements FMCommandParser {

    private String name;
    private List<String> params = new ArrayList<String>();

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setParams(String... params) {
        this.params.addAll(Arrays.asList(params));
    }

    /**
     * {@inheritDoc}
     */
    public String[] getParams() {
        String[] array = new String[params.size()];
        params.toArray(array);
        return array;
    }


    public static FMCommandDefaultParser getDefault() {
        return new FMCommandDefaultParser();
    }

    /**
     * {@link FMCommandDefaultParser}默认解析器使用"_"分隔符 解析 payload
     * <br/>
     * <br/>
     * {@inheritDoc}
     */
    public FMCommandDefaultParser parse(String payload) {
        String[] split = StringUtils.split(payload, "_");
        if (split != null && split.length >= 1) {
            FMCommandDefaultParser fmpCommandParser = new FMCommandDefaultParser();
            fmpCommandParser.setName(split[0]);
            if (split.length >= 2) {
                for (int i = 1; i < split.length; i++) {
                    fmpCommandParser.params.add(split[i]);
                }
            }
            return fmpCommandParser;
        } else {
            return new FMCommandDefaultParser();
        }
    }

    /**
     * split with symbol "_"
     *
     * @param source    keywords payload
     * @param arguments some arguments
     * @return renamed Command Name
     */
    public String toCommand(String source, String... arguments) {
        String join = Joiner.on("_").join(arguments);
        if (StringUtils.isNotBlank(join)) {
            source = source + "_" + join;
        }
        return source;
    }
}
