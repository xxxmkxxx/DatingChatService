package com.dating.chat.controller;

import com.dating.chat.data.MessageData;
import com.dating.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    public static final String MESSAGES_REQUEST = "/{dialog}";
    public static final String CREATE_MESSAGE_REQUEST = "/";
    private final MessageService messageService;

    @GetMapping(MESSAGES_REQUEST)
    public ResponseEntity<List<MessageData>> getMessages(@PathVariable String dialog) {
        return ResponseEntity.ok(messageService.getMessages(dialog));
    }

    @PostMapping(CREATE_MESSAGE_REQUEST)
    public ResponseEntity<String> createMessage(@RequestBody MessageData data) {
        return ResponseEntity.ok(messageService.createMessage(data));
    }
}
