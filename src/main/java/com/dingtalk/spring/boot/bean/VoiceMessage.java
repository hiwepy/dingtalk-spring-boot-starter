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

import java.time.Duration;

/**
 * 语音消息类型
 */
@SuppressWarnings("serial")
public class VoiceMessage extends BaseMessage {

    /**
     * 正整数，小于60，表示音频时长
     */
    private Duration duration;
    /**
     * 媒体文件id。2MB，播放长度不超过60s，AMR格式
     */
    private String mediaId;

    public VoiceMessage() {
        super(MessageType.image);
    }

    public VoiceMessage(String mediaId, Duration duration) {
    	super(MessageType.link);
        this.mediaId = mediaId;
        this.duration = duration;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}