package com.dating.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class MessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "sender", nullable = false)
    private String sender;

    @Column(name = "text_data", nullable = false)
    private String textData;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private DialogModel dialog;
}
