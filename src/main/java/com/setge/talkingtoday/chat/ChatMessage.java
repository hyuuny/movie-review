package com.setge.talkingtoday.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private MessageType type;
    private String sender;
    private String content;

}
