package com.solver.solver_be.domain.questionBoard.repository;

import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long>, CustomQuestionBoardRepository {
    List<QuestionBoard> findAllByOrderByCreatedAtDesc();
    List<QuestionBoard> findAllByMindMapId(Long mindMapId);
    Optional<List<QuestionBoard>> findByParentBoardId(Long id);
}
