package com.solver.solver_be.domain.business.repository;

import com.solver.solver_be.domain.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BussinessRepository extends JpaRepository<Business, Long> {
}
