package com.solver.solver_be.domain.user.repository;

import com.solver.solver_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}