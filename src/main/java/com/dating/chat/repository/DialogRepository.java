package com.dating.chat.repository;

import com.dating.chat.model.DialogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<DialogModel, Long> {
    DialogModel findByPublicCode(String publicCode);
    boolean existsByPublicCode(String publicCode);
    boolean existsDialogModelByFirstLoginAndSecondLogin(String firstLogin, String secondLogin);
}
