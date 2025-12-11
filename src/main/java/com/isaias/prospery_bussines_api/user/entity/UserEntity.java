package com.isaias.prospery_bussines_api.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isaias.prospery_bussines_api.common.enums.RoleEnum;
import com.isaias.prospery_bussines_api.trace.entity.TraceEntity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private boolean active = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(nullable = true, length = 6)
    @JsonIgnore
    private String verificationCode;

    @Column(nullable = false, length = 10)
    @JsonIgnore
    private String chatId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<TraceEntity> trace = new ArrayList<>();
}
