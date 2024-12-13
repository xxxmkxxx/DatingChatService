package com.dating.chat.repository;

import com.dating.chat.model.DialogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<DialogModel, Long> {
    Optional<DialogModel> findByPublicCodeAndActive(String publicCode, boolean active);
    boolean existsDialogModelByFirstLoginAndSecondLogin(String firstLogin, String secondLogin);
    List<DialogModel> findAllByActive(boolean active);
}
