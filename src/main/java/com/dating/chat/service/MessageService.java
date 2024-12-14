package com.dating.chat.service;

import com.dating.chat.common.exception.DialogNotFoundException;
import com.dating.chat.data.MessageData;
import com.dating.chat.mapper.MessageDataMapper;
import com.dating.chat.model.DialogModel;
import com.dating.chat.model.MessageModel;
import com.dating.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final DialogService dialogService;
    private final MessageRepository messageRepository;
    private final KafkaTemplate<String, MessageData> messageKafkaTemplate;
    public static final String CREATE_MESSAGE_TOPIC = "create_message";

    public List<MessageData> getMessages(String dialogCode) {
        return messageRepository.getAllByDialogPublicCode(dialogCode).stream()
                .map(MessageDataMapper::map)
                .toList();
    }

    public String createMessage(MessageData messageData) {
        //TODO: add message validation

        DialogModel dialog = dialogService.getDialogModel(messageData.getDialogCode());
        MessageModel message = updateMessage(messageData);

        dialog.addMessage(messageRepository.save(message));
        dialogService.updateDialog(dialog);

        return "Сообщение успешно создано!";
    }

    public MessageData createStompMessage(MessageData messageData) {
        messageKafkaTemplate.send(CREATE_MESSAGE_TOPIC, messageData);

        return messageData;
    }

    @KafkaListener(topics = CREATE_MESSAGE_TOPIC, groupId = "dating")
    public void handleCreateMessageEvent(MessageData messageData) {
        try {
            createMessage(messageData);
        } catch (DialogNotFoundException e) {
            log.debug(e.getMessage());
        }
    }

    private MessageModel updateMessage(MessageData messageData) {
        return messageRepository.findByDialogPublicCode(messageData.getDialogCode())
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
