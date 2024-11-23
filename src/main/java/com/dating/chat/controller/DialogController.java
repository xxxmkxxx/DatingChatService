package com.dating.chat.controller;

import com.dating.chat.data.NewDialogData;
import com.dating.chat.data.ResponseData;
import com.dating.chat.service.DialogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0.0.1/dialogs")
@AllArgsConstructor
public class DialogController {
    private DialogService dialogService;

    @PostMapping("/")
    public ResponseEntity<ResponseData<String>> createDialog(@RequestBody NewDialogData data) {
        return ResponseEntity.ok(dialogService.createDialog(data));
    }
}
