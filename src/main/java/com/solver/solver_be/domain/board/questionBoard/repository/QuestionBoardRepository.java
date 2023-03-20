package com.solver.solver_be.domain.board.questionBoard.repository;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {
    List<QuestionBoard> findAllByOrderByCreatedAtDesc();
    Optional<List<QuestionBoard>> findByParentBoardId(Long id);
}
