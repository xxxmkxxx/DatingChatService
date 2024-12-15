package com.dating.chat.service;

import com.dating.chat.common.exception.CreateDialogConflictException;
import com.dating.chat.common.exception.DialogNotFoundException;
import com.dating.chat.common.DialogPublicCodeGenerator;
import com.dating.chat.data.CreateDialogResponseData;
import com.dating.chat.data.DialogData;
import com.dating.chat.data.NewDialogData;
import com.dating.chat.mapper.CreateDialogResponseMapper;
import com.dating.chat.mapper.DialogDataMapper;
import com.dating.chat.model.DialogModel;
import com.dating.chat.repository.DialogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DialogService {
    private DialogRepository dialogRepository;
    private DialogPublicCodeGenerator codeGenerator;

    public CreateDialogResponseData createDialog(NewDialogData data) {
        boolean firstSecondFlag = dialogRepository.existsDialogModelByFirstLoginAndSecondLogin(data.getSender(), data.getRecipient());
        boolean secondFirstFlag = dialogRepository.existsDialogModelByFirstLoginAndSecondLogin(data.getRecipient(), data.getSender());
        if (firstSecondFlag || secondFirstFlag) {
            throw new CreateDialogConflictException("Такой диалог уже существует!");
        }

        DialogModel dialog = new DialogModel();
        dialog.setActive(true);
        dialog.setIconPath(data.getIconPath());
        dialog.setFirstLogin(data.getSender());
        dialog.setSecondLogin(data.getRecipient());
        dialog.setPublicCode(codeGenerator.generate(7));
        dialog.setMessages(new ArrayList<>());

        return CreateDialogResponseMapper.map(dialogRepository.save(dialog));
    }

    public DialogData getDialog(String dialogCode) {
        return dialogRepository.findByPublicCodeAndActive(dialogCode, true)
                .map(DialogDataMapper::map)
                .orElseThrow(() -> new DialogNotFoundException("Диалог с таким кодом не найден!"));
    }

    public List<DialogData> getDialogs() {
        return dialogRepository.findAllByActive(true).stream()
                .map(DialogDataMapper::map)
                .toList();
    }

    public DialogModel getDialogModel(String dialogCode) {
        return dialogRepository.findByPublicCodeAndActive(dialogCode, true)
                .orElseThrow(() -> new DialogNotFoundException("Диалог с таким кодом не найден!"));
    }

    public String blockDialog(String dialogCode) {
        DialogModel dialog = getDialogModel(dialogCode);
        dialog.setActive(false);
        updateDialog(dialog);

        return "Диалог успешно заблокирован!";
    }

    public void updateDialog(DialogModel dialog) {
        dialogRepository.save(dialog);
    }
}
