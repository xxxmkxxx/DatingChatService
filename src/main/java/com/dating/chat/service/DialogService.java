package com.dating.chat.service;

import com.dating.chat.common.DialogPublicCodeGenerator;
import com.dating.chat.data.NewDialogData;
import com.dating.chat.data.ResponseData;
import com.dating.chat.model.DialogModel;
import com.dating.chat.repository.DialogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class DialogService {
    private DialogRepository dialogRepository;
    private DialogPublicCodeGenerator codeGenerator;

    public ResponseData<String> createDialog(NewDialogData data) {
        boolean firstSecondFlag = dialogRepository.existsDialogModelByFirstLoginAndSecondLogin(data.getSender(), data.getRecipient());
        boolean secondFirstFlag = dialogRepository.existsDialogModelByFirstLoginAndSecondLogin(data.getRecipient(), data.getSender());
        if (firstSecondFlag || secondFirstFlag) {
            return new ResponseData<>(false, "This dialogue already exists!", null);
        }

        DialogModel dialog = new DialogModel();
        dialog.setActive(true);
        dialog.setFirstLogin(data.getSender());
        dialog.setSecondLogin(data.getRecipient());
        dialog.setPublicCode(codeGenerator.generate(7));
        dialog.setMessages(new HashSet<>());

        dialogRepository.save(dialog);

        return new ResponseData<>(true, "Dialogue successfully created!", dialog.getPublicCode());
    }

    public boolean existsDialog(String dialogCode) {
        return dialogRepository.existsByPublicCode(dialogCode);
    }

    public DialogModel getDialog(String dialogCode) {
        return dialogRepository.findByPublicCode(dialogCode).orElse(new DialogModel());
    }
}
