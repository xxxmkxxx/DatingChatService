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
    private DialogRepository repository;
    private DialogPublicCodeGenerator codeGenerator;

    public ResponseData<String> createDialog(NewDialogData data) {
        boolean firstSecondFlag = repository.existsDialogModelByFirstLoginAndSecondLogin(data.getSender(), data.getRecipient());
        boolean secondFirstFlag = repository.existsDialogModelByFirstLoginAndSecondLogin(data.getRecipient(), data.getSender());
        if (firstSecondFlag || secondFirstFlag) {
            return new ResponseData<>(false, "This dialogue already exists!", null);
        }

        DialogModel dialog = new DialogModel();
        dialog.setActive(true);
        dialog.setFirstLogin(data.getSender());
        dialog.setSecondLogin(data.getRecipient());
        dialog.setPublicCode(codeGenerator.generate(7));
        dialog.setMessages(new HashSet<>());

        repository.save(dialog);

        return new ResponseData<>(true, "Dialogue successfully created!", dialog.getPublicCode());
    }

    public boolean existsDialog(String dialogCode) {
        return repository.existsByPublicCode(dialogCode);
    }

    public ResponseData<DialogModel> getDialog(String dialogCode) {
        return repository.findByPublicCode(dialogCode)
                .map(model -> new ResponseData<>(true, null, model))
                .orElse(new ResponseData<>(false, "Such dialogue does not exist or you do not have access to it!", null));
    }

    public ResponseData<?> updateDialog(DialogModel dialog) {
        repository.save(dialog);
        return new ResponseData<>(true, "Dialogue successfully changed!", null);
    }
}
