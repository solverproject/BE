package com.solver.solver_be.global.security.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findAllByUserEmail(String userEmail);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
