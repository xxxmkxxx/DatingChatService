package com.dating.chat.service;

import com.dating.chat.data.MessageData;
import com.dating.chat.data.ResponseData;
import com.dating.chat.data.RestrictionsNumberMessagesData;
import com.dating.chat.mapper.MessageDataMapper;
import com.dating.chat.model.MessageModel;
import com.dating.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private MessageRepository repository;

    public ResponseData<List<MessageData>> getMessages(String dialogCode, RestrictionsNumberMessagesData data) {
        if (data.isAll()) {
            return new ResponseData<>(
                    true, null,
                    repository.getAllByDialogPublicCode(dialogCode).stream()
                            .map(MessageDataMapper::map)
                            .toList()
            );
        } else if (data.getAmount() > repository.countAllByDialogPublicCode(dialogCode)) {
            return new ResponseData<>(
                    true, null,
                    repository.findLastRecordsWithAmount(repository.countAllByDialogPublicCode(dialogCode), dialogCode).stream()
                            .map(MessageDataMapper::map)
                            .toList()
            );
        } else {
            return new ResponseData<>(
                    true, null,
                    repository.findLastRecordsWithAmount(data.getAmount(), dialogCode).stream()
                            .map(MessageDataMapper::map)
                            .toList()
            );
        }
    }

    public ResponseData<MessageModel> createMessage(MessageData data) {
        MessageModel message = new MessageModel();
        message.setTextData(data.getText());
        message.setSender(data.getSender());

        return new ResponseData<>(true, "Message send successfully!", repository.save(message));
    }

    public ResponseData<?> updateMessage(MessageModel message) {
        repository.save(message);
        return new ResponseData<>(true, "Message successfully changed!", null);
    }
}
