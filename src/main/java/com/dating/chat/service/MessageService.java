package com.dating.chat.service;

import com.dating.chat.data.MessageData;
import com.dating.chat.mapper.MessageDataMapper;
import com.dating.chat.model.DialogModel;
import com.dating.chat.model.MessageModel;
import com.dating.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final DialogService dialogService;
    private final MessageRepository messageRepository;

    public List<MessageData> getMessages(String dialogCode) {
        return messageRepository.getAllByDialogPublicCode(dialogCode).stream()
                .map(MessageDataMapper::map)
                .toList();
    }

    public String createMessage(String dialogCode, MessageData messageData) {
        DialogModel dialog = dialogService.getDialogModel(dialogCode);
        MessageModel message = updateMessage(dialogCode, messageData);

        dialog.addMessage(messageRepository.save(message));
        dialogService.updateDialog(dialog);

        return "Сообщение успешно создано!";
    }

    private MessageModel updateMessage(String dialogCode, MessageData messageData) {
        return messageRepository.findByDialogPublicCode(dialogCode)
                .map(messageModel -> {
                    messageModel.setTextData(messageData.getText());
                    messageModel.setSender(messageData.getSender());

                    return messageModel;
                })
                .orElseGet(() -> {
                    MessageModel messageModel = new MessageModel();
                    messageModel.setTextData(messageData.getText());
                    messageModel.setSender(messageData.getSender());

                    return messageModel;
                });
    }
}
