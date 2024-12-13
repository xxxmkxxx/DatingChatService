package com.dating.chat.mapper;

import com.dating.chat.data.CreateDialogResponseData;
import com.dating.chat.model.DialogModel;

public class CreateDialogResponseMapper {
    public static CreateDialogResponseData map(DialogModel dialogModel) {
        return CreateDialogResponseData.builder()
                .code(dialogModel.getPublicCode())
                .build();
    }
}
