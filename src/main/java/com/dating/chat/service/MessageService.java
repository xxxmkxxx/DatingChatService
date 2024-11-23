package com.dating.chat.service;

import com.dating.chat.data.MessageData;
import com.dating.chat.data.ResponseData;
import com.dating.chat.data.RestrictionsNumberMessagesData;
import com.dating.chat.mapper.MessageDataMapper;
import com.dating.chat.model.DialogModel;
import com.dating.chat.model.MessageModel;
import com.dating.chat.repository.DialogRepository;
import com.dating.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private MessageRepository messageRepository;
    private DialogRepository dialogRepository;

    public ResponseData<List<MessageData>> getDialogMessages(String dialogCode, RestrictionsNumberMessagesData data) {
        if (data.isAll()) {
            return new ResponseData<>(
                    true, null,
                    messageRepository.getAllByDialogPublicCode(dialogCode).stream()
                            .map(MessageDataMapper::map)
                            .toList()
            );
        } else if (!dialogRepository.existsByPublicCode(dialogCode)) {
            return new ResponseData<>(false, "Dialogue error!", null);
        } else if (data.getAmount() > messageRepository.countAllByDialogPublicCode(dialogCode)) {
            return new ResponseData<>(
                    true, null,
                    messageRepository.findLastRecordsWithAmount(messageRepository.countAllByDialogPublicCode(dialogCode), dialogCode).stream()
                            .map(MessageDataMapper::map)
                            .toList()
            );
        } else {
            return new ResponseData<>(
                    true, null,
                    messageRepository.findLastRecordsWithAmount(data.getAmount(), dialogCode).stream()
                            .map(MessageDataMapper::map)
                            .toList()
            );
        }
    }

    public ResponseData<Void> createMessage(MessageData data) {
        if (!dialogRepository.existsByPublicCode(data.getDialogCode())) {
            return new ResponseData<>(false, "This dialog does not exist or you do not have access to it!", null);
        }

        DialogModel dialog = dialogRepository.findByPublicCode(data.getDialogCode());
        MessageModel message = new MessageModel();
        message.setTextData(data.getText());
        message.setSender(data.getSender());
        message.setDialog(dialog);

        dialog.getMessages().add(messageRepository.save(message));

        return new ResponseData<>(true, "Message send successfully!", null);
    }
}
