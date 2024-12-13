package com.dating.chat.controller;

import com.dating.chat.data.MessageData;
import com.dating.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompMessageController {
    public static final String CREATE_MESSAGE_STOMP_EVENT = "/message";
    public static final String CREATE_MESSAGE_STOMP_EVENT_LISTENER = "/topic/messages";
    private final MessageService messageService;

    @MessageMapping(CREATE_MESSAGE_STOMP_EVENT)
    @SendTo(CREATE_MESSAGE_STOMP_EVENT_LISTENER)
    public String sendMessage(MessageData messageData) {
        return messageService.createStompMessage(messageData);
    }
}
