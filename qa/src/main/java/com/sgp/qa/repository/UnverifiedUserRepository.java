package com.sgp.qa.repository;

import com.sgp.qa.model.UnverifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnverifiedUserRepository extends JpaRepository<UnverifiedUser, Long> {
    Optional<UnverifiedUser> findByEmail(String email);
    Optional<UnverifiedUser> findByOtp(String otp);
}

