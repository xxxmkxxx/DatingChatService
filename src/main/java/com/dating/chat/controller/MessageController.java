package com.dating.chat.controller;

import com.dating.chat.data.MessageData;
import com.dating.chat.data.ResponseData;
import com.dating.chat.data.RestrictionsNumberMessagesData;
import com.dating.chat.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0.0.1/messages")
@AllArgsConstructor
public class MessageController {
    private MessageService messageRepository;

    @GetMapping("/{dialogCode}")
    public ResponseEntity<ResponseData<List<MessageData>>> getDialogMessages(@PathVariable String dialogCode, @RequestBody RestrictionsNumberMessagesData data) {
        return ResponseEntity.ok(messageRepository.getDialogMessages(dialogCode, data));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseData<Void>> createMessage(@RequestBody MessageData data) {
        return ResponseEntity.ok(messageRepository.createMessage(data));
    }
}
