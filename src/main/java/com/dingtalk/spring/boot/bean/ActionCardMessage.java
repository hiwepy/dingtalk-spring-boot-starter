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

import java.util.ArrayList;
import java.util.List;

/**
 * 跳转卡片类型
 */
@SuppressWarnings("serial")
public class ActionCardMessage extends BaseMessage {

    /**
     * 钉钉官网给的SDK上的按钮最多5个，最少1个。
     * 但是经过实际测试(PS.只测试到30个，后续未测)，按钮理论上可以为0到无限多个。
     * 当按钮数目超过2两个时，手机端按钮一定是垂直排列，PC端则可以保持设定的排列方式进行排列。
     * 当按钮数目过多时，无论是PC端还是手机端，排列布局对用户来说都不是很友好，
     * 故为了美观考虑，我们这里的最大值和最小值分别设置为5和0
     */
    private static final int MAX_BUTTON_COUNT = 5;
    private static final int MIN_BUTTON_COUNT = 0;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文，支持MarkDown语法
     */
    private String text;

    /**
     * 是否隐藏头像
     * 0 - 不隐藏头像
     * 1 - 隐藏头像
     */
    private HideAvatarType hideAvatar = HideAvatarType.UNHIDE;

    /**
     * 按钮排列方式
     * 0 - 垂直排列
     * 1 - 水平排列
     */
    private ButtonOrientationType btnOrientation = ButtonOrientationType.HORIZONTAL;

    /**
     * 是否为按钮跳转布局，默认为false。
     * 但只在Button个数为1时，分辨布局方式起作用。
     * true - 使用独立跳转ActionCard发消息
     * false - 使用整体跳转ActionCard发消息
     */
    private boolean isButtonView;

    /**
     * 操作按钮成员变量
     * 这个成员变量没有Set方法，主要是为了防止按钮过多造成的较差体验
     */
    private List<ActionCardButton> buttons = new ArrayList<>();

    public ActionCardMessage() {
    	super(MessageType.actionCard);
    }

    public ActionCardMessage(String title, String text) {
    	super(MessageType.actionCard);
        this.title = title;
        this.text = text;
    }

    public ActionCardMessage(String title, String text, HideAvatarType hideAvatar) {
    	super(MessageType.actionCard);
        this.title = title;
        this.text = text;
        this.hideAvatar = hideAvatar;
    }

    public ActionCardMessage(String title, String text, ActionCardButton button) {
    	super(MessageType.actionCard);
        this.title = title;
        this.text = text;
        this.buttons.add(button);
    }

    public ActionCardMessage(String title, String text, HideAvatarType hideAvatar, ActionCardButton button) {
    	super(MessageType.actionCard);
        this.title = title;
        this.text = text;
        this.hideAvatar = hideAvatar;
        this.buttons.add(button);
    }
 
    /**
     * 增加操作按钮
     *
     * @param button
     */
    public void addButton(ActionCardButton button) {
        if (button == null) {
            throw new IllegalArgumentException("not allow add empty button");
        }
        if (buttons == null || buttons.size() >= MAX_BUTTON_COUNT) {
            throw new IllegalArgumentException("the number of buttons is not advise bigger than " + MAX_BUTTON_COUNT);
        }
        buttons.add(button);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HideAvatarType getHideAvatar() {
        return hideAvatar;
    }

    public void setHideAvatar(HideAvatarType hideAvatar) {
        this.hideAvatar = hideAvatar;
    }

    public ButtonOrientationType getBtnOrientation() {
        return btnOrientation;
    }

    public void setBtnOrientation(ButtonOrientationType btnOrientation) {
        this.btnOrientation = btnOrientation;
    }

    public List<ActionCardButton> getButtons() {
        return buttons;
    }

    public boolean isButtonView() {
        return isButtonView;
    }

    public void setButtonView(boolean buttonView) {
        isButtonView = buttonView;
    }
}