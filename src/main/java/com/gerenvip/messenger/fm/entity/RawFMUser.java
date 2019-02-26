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
package com.gerenvip.messenger.fm.entity;

import lombok.Data;

/**
 * 定义 返回的原始 Messenger 用户的 Profile 信息 字段,
 */
@Data
public class RawFMUser {
    private String id;
    private String name;
    private String first_name;
    private String last_name;
    private String profile_pic;
    private String locale;
    private int timezone;
    private String gender;
    private String is_payment_enabled;
    private String email;
}
