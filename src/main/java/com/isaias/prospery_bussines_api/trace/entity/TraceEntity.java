package com.isaias.prospery_bussines_api.trace.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.isaias.prospery_bussines_api.trace.enums.TraceEnum;
import com.isaias.prospery_bussines_api.user.entity.UserEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "traces")
@Data
public class TraceEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TraceEnum type;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private String description;

    @Column(nullable = false, length = 20)
    private String tableName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
