package com.dating.chat.mapper;

import com.dating.chat.data.DialogData;
import com.dating.chat.model.DialogModel;

public class DialogDataMapper {
    public static DialogData map(DialogModel dialog) {
        return DialogData.builder()
                .code(dialog.getPublicCode())
                .iconPath(dialog.getIconPath())
                .firstLogin(dialog.getFirstLogin())
                .secondLogin(dialog.getSecondLogin())
                .build();
    }
}
