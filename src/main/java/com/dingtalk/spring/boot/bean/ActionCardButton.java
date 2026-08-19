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
 * Represents a button in a DingTalk action card message.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class ActionCardButton {

    /** The button title displayed to the user. */
    private String title;
    /** The URL to navigate to when the button is clicked. */
    private String actionURL;

    public ActionCardButton() {
    }

    public ActionCardButton(String title, String actionURL) {
        this.title = title;
        this.actionURL = actionURL;
    }

    /**
     * <p>Default read button.</p>
     * @param actionURL
     * @return the result
     */
    public static ActionCardButton defaultReadButton(String actionURL) {
        ActionCardButton button = new ActionCardButton();
        button.setTitle("阅读全文");
        button.setActionURL(actionURL);
        return button;
    }

    /** @return return the title. */
    public String getTitle() {
        return title;
    }

    /** @param title set the title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return return the action u r l. */
    public String getActionURL() {
        return actionURL;
    }

    /** @param actionURL set the action u r l. */
    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }
}
