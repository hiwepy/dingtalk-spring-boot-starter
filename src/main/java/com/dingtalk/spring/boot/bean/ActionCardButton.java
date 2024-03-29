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
 * 跳转卡片中的按钮实体类
 */
public class ActionCardButton {

    /**
     * 按钮标题
     */
    private String title;

    /**
     * 实际点击时调用的URL
     */
    private String actionURL;

    public ActionCardButton() {
    }

    public ActionCardButton(String title, String actionURL) {
        this.title = title;
        this.actionURL = actionURL;
    }

    public static ActionCardButton defaultReadButton(String actionURL) {
        ActionCardButton button = new ActionCardButton();
        button.setTitle("阅读全文");
        button.setActionURL(actionURL);
        return button;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }
}
