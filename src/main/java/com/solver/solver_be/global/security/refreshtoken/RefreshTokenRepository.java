package com.solver.solver_be.global.security.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findAllByUserEmail(String userEmail);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
