package com.solver.solver_be.domain.board.answerBoard.entity;

import com.solver.solver_be.domain.board.answerBoard.dto.AnswerRequestDto;
import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.util.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class AnswerBoard extends TimeStamped {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "QUESTIONBOARD_ID", nullable = false)
    private QuestionBoard questionBoard;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private AnswerBoard(QuestionBoard questionBoard, User user, String contents){
        this.questionBoard = questionBoard;
        this.user = user;
        this.contents = contents;
    }

    public static AnswerBoard of(QuestionBoard questionBoard, User user, AnswerRequestDto answerRequestDto){
        return AnswerBoard.builder()
                .questionBoard(questionBoard)
                .user(user)
                .contents(answerRequestDto.getContents())
                .build();
    }
}
