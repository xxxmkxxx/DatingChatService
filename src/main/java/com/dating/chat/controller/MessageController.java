package com.dating.chat.controller;

import com.dating.chat.data.MessageData;
import com.dating.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    public static final String MESSAGES_REQUEST = "/{dialog}";
    public static final String CREATE_MESSAGE_REQUEST = "/{dialog}";
    private final MessageService messageService;

    @GetMapping(MESSAGES_REQUEST)
    public ResponseEntity<List<MessageData>> getMessages(@PathVariable String dialog) {
        return ResponseEntity.ok(messageService.getMessages(dialog));
    }

    @PostMapping(CREATE_MESSAGE_REQUEST)
    public ResponseEntity<String> createMessage(@PathVariable String dialog, @RequestBody MessageData data) {
        return ResponseEntity.ok(messageService.createMessage(dialog, data));
    }

    @MessageMapping("/chat/dialog/{dialog}")
    @SendTo("/topic/dialog/{dialog}/messages")
    public String sendMessage(@DestinationVariable String dialog, String message, Principal principal) {
        return message;
    }
}
