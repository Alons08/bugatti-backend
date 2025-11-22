package com.alocode.usuario_service.repository;

import com.alocode.usuario_service.entity.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {
    Optional<PasswordResetCode> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
