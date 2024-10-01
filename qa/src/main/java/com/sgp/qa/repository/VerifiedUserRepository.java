package com.sgp.qa.repository;

import com.sgp.qa.model.VerifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifiedUserRepository extends JpaRepository<VerifiedUser, Long> {
    Optional<VerifiedUser> findByEmail(String email);
    Optional<VerifiedUser> findByUsername(String username);
}

