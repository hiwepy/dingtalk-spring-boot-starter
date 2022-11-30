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
 * 图片消息类型
 */
@SuppressWarnings("serial")
public class ImageMessage extends BaseMessage {

    /**
     * 媒体文件id。引用的媒体文件最大10MB
     */
    private String mediaId;

    public ImageMessage() {
        super(MessageType.image);
    }

    public ImageMessage(String mediaId) {
    	super(MessageType.link);
        this.mediaId = mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

}