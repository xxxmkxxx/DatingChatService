package com.dating.chat.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetMessagesResponseData {
    private int amount;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean all;
}
