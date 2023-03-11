package com.solver.solver_be.domain.board.answerBoard.repository;

import com.solver.solver_be.domain.board.answerBoard.entity.AnswerBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerBoardRepository extends JpaRepository<AnswerBoard, Long> {
}
