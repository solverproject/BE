package com.solver.solver_be.domain.follow.repository;

import com.solver.solver_be.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByUserIdAndFollowUserId(Long id, Long id1);

    void deleteByUserIdAndFollowUserId(Long id, Long id1);
}
