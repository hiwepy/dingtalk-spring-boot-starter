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
 * Enumeration of DingTalk message types: text, link, markdown, action card, and feed card.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public enum MessageType {

    /** Plain text message. */
    text,
    /** Link message with title, text, and URL. */
    link,
    /** Markdown formatted message. */
    markdown,
    /** Action card message with interactive buttons. */
    actionCard,
    /** Feed card message with multiple link items. */
    feedCard;
}
