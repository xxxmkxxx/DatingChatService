package com.dating.chat.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DialogData {
    private String code;
    private String iconPath;
    private String firstLogin;
    private String secondLogin;
}
