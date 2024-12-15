package com.dating.chat.data;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewDialogData {
    private String sender;
    private String recipient;
    private String iconPath;
}
