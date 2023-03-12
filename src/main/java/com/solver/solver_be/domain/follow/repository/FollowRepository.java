package com.solver.solver_be.domain.follow.repository;

import com.solver.solver_be.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
