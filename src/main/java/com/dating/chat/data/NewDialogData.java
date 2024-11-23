package com.dating.chat.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewDialogData {
    private String sender;
    private String recipient;
}
