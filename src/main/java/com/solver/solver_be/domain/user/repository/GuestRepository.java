package com.solver.solver_be.domain.user.repository;

import com.solver.solver_be.domain.user.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByUserId(String userId);
    Optional<Guest> findByName(String username);

}