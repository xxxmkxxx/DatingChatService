package com.dating.chat.service;

import com.dating.chat.data.MessageData;
import com.dating.chat.data.ResponseData;
import com.dating.chat.model.DialogModel;
import com.dating.chat.model.MessageModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DialogWithMessageService {
    private DialogService dialogService;
    private MessageService messageService;

    public ResponseData<?> createDialogMessage(MessageData data) {
        ResponseData<DialogModel> dialogResponseData = dialogService.getDialog(data.getDialogCode());
        if (!dialogResponseData.isSuccess()) {
            return new ResponseData<>(false, dialogResponseData.getDescription(), null);
        }

        ResponseData<MessageModel> messageResponseData = messageService.createMessage(data);
        if (!messageResponseData.isSuccess()) {
            return new ResponseData<>(false, messageResponseData.getDescription(), null);
        }

        DialogModel dialog = dialogResponseData.getData();
        MessageModel message = messageResponseData.getData();

        message.setDialog(dialog);
        dialog.getMessages().add(message);

        ResponseData<?> dialogResponse = dialogService.updateDialog(dialog);
        if (!dialogResponseData.isSuccess()) {
            return new ResponseData<>(false, dialogResponse.getDescription(), null);
        }

        ResponseData<?> messageResponse = messageService.updateMessage(message);
        if (!messageResponseData.isSuccess()) {
            return new ResponseData<>(false, messageResponse.getDescription(), null);
        }


        return new ResponseData<>(true, "Message successfully created!", null);
    }
}
