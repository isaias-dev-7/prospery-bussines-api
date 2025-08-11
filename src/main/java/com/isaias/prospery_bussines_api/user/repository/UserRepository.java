package com.isaias.prospery_bussines_api.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isaias.prospery_bussines_api.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    Optional<UserEntity> findByPhone(String phone);

    boolean existsByUsernameAndActiveTrue(String username);

    Optional<UserEntity> findByVerificationCode(String code);
}
