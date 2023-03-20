package com.solver.solver_be.domain.board.answerBoard.repository;

import com.solver.solver_be.domain.board.answerBoard.entity.AnswerBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerBoardRepository extends JpaRepository<AnswerBoard, Long> {
    List<AnswerBoard> findAllByQuestionBoardId(Long questionBoardId);

    void deleteAllByQuestionBoardId(Long id);
}
