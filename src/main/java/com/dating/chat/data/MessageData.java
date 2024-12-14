package com.dating.chat.data;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageData {
    private String dialogCode;
    private String sender;
    private String text;
    private LocalDateTime dateTime;
}
