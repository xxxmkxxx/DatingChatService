package com.dating.chat.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "dialog")
public class DialogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dialog_id")
    private Long id;

    @Column(name = "public_code", nullable = false)
    private String publicCode;

    @Column(name = "sender", nullable = false)
    private String firstLogin;

    @Column(name = "recipient", nullable = false)
    private String secondLogin;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "dialog")
    private Set<MessageModel> messages;
}
