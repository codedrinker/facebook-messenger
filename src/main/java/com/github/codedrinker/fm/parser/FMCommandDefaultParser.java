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

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FMCommandDefaultParser implements FMCommandParser {

    private String name;
    private List<String> params = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParams(String... params) {
        for (String param : params) {
            this.params.add(param);
        }
    }

    public String[] getParams() {
        String[] array = new String[params.size()];
        params.toArray(array);
        return array;
    }

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

    public String print(String source, String... arguments) {
        String join = Joiner.on("_").join(arguments);
        if (StringUtils.isNotBlank(join)) {
            source = source + "_" + join;
        }
        return source;
    }
}
