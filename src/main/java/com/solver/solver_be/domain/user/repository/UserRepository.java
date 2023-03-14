package com.solver.solver_be.domain.user.repository;

import com.solver.solver_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByKakaoId(Long id);

    Optional<User> findByGoogleId(Long id);

    Optional<User> findByNaverId(String id);
}