package com.egs.bankservice.model.repository;

import com.egs.bankservice.model.entity.EgsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<EgsUser, Long> {
    Optional<EgsUser> findByUsername(String username);
    Optional<EgsUser> findByCardNumber(String username);
}
