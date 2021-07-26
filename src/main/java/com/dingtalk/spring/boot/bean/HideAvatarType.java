/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dingtalk.spring.boot.bean;

/**
 * ActionCard消息是否隐藏头像枚举值
 */
public enum HideAvatarType {

    /**
     * 发消息的时候，隐藏机器人头像
     */
    HIDE("隐藏", "1"),

    /**
     * 发消息的时候，显示机器人头像
     */
    UNHIDE("不隐藏，正常显示", "0");
    private String comment;

    private String value;

    HideAvatarType(String comment, String value) {
        this.comment = comment;
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public String getValue() {
        return value;
    }


}
