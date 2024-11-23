package com.dating.chat.mapper;

import com.dating.chat.data.MessageData;
import com.dating.chat.model.MessageModel;

public class MessageDataMapper {
    public static MessageData map(MessageModel model) {
        return MessageData.builder()
                .sender(model.getSender())
                .text(model.getTextData())
                .dateTime(model.getCreateTime())
                .dialogCode(model.getDialog().getPublicCode())
                .build();
    }
}
