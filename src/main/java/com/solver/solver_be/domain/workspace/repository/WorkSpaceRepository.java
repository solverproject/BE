package com.solver.solver_be.domain.workspace.repository;

import com.solver.solver_be.domain.workspace.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace,Long> {
}
