package com.solver.solver_be.domain.mindmap.repository;

import com.solver.solver_be.domain.mindmap.entity.MindMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MindMapRepository extends JpaRepository<MindMap, Long> {
    List<MindMap> findAllByWorkSpaceId(Long id);
}
