package com.solver.solver_be.domain.board.questionBoard.repository;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {
}
