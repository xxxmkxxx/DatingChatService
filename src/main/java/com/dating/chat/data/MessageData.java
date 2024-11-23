package com.dating.chat.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageData {
    private String sender;
    private String dialogCode;
    private String text;
    private LocalDateTime dateTime;
}
