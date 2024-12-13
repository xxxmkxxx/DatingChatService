package com.dating.chat.repository;

import com.dating.chat.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {
    List<MessageModel> getAllByDialogPublicCode(String publicCode);
    @Query(value = "SELECT * FROM message WHERE public_code = :publicCode ORDER BY message_id DESC LIMIT :amount", nativeQuery = true)
    List<MessageModel> findLastRecordsWithAmount(@Param("amount") int amount, @Param("publicCode") String publicCode);

    int countAllByDialogPublicCode(String publicCode);

    Optional<MessageModel> findByDialogPublicCode(String publicCode);
}
