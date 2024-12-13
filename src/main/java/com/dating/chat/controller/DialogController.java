package com.dating.chat.controller;

import com.dating.chat.data.CreateDialogResponseData;
import com.dating.chat.data.DialogData;
import com.dating.chat.data.NewDialogData;
import com.dating.chat.service.DialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {
    public static final String DIALOGS_REQUEST = "/";
    public static final String DIALOG_REQUEST = "/{dialog}";
    public static final String CREATE_DIALOG_REQUEST = "/";
    public static final String BLOCK_DIALOG_REQUEST = "/{dialog}/block";
    private final DialogService dialogService;

    @PostMapping(CREATE_DIALOG_REQUEST)
    public ResponseEntity<CreateDialogResponseData> createDialog(@RequestBody NewDialogData data) {
        return ResponseEntity.ok(dialogService.createDialog(data));
    }

    @GetMapping(DIALOG_REQUEST)
    public ResponseEntity<DialogData> getDialog(@PathVariable String dialog) {
        return ResponseEntity.ok(dialogService.getDialog(dialog));
    }

    @GetMapping(DIALOGS_REQUEST)
    public ResponseEntity<List<DialogData>> getDialogs() {
        return ResponseEntity.ok(dialogService.getDialogs());
    }

    @PostMapping(BLOCK_DIALOG_REQUEST)
    public ResponseEntity<String> blockDialog(@PathVariable String dialog) {
        return ResponseEntity.ok(dialogService.blockDialog(dialog));
    }
}
