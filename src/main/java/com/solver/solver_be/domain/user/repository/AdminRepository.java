package com.solver.solver_be.domain.user.repository;


import com.solver.solver_be.domain.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUserId(String userId);
    Optional<Admin> findByName(String username);

}