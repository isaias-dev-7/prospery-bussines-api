package com.isaias.prospery_bussines_api.user.entity;

import com.isaias.prospery_bussines_api.common.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Getter @Setter
    private UUID id;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String username;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String email;

    private String phone;

    @Column(nullable = false)
    @Getter @Setter
    private String password;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
