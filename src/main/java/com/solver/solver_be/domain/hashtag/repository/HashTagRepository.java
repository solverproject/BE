package com.solver.solver_be.domain.hashtag.repository;

import com.solver.solver_be.domain.hashtag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findByQuestionBoardId(Long id);

    void deleteAllByQuestionBoardId(Long id);
}
