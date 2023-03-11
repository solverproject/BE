package com.solver.solver_be.domain.hashtag.entity;

import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "QUESTIONBOARD_ID", nullable = false)
    private QuestionBoard questionBoard;

    @Column(nullable = false)
    private String title;

    @Builder
    public HashTag(String title, QuestionBoard questionBoard){
        this.title = title;
        this.questionBoard = questionBoard;
    }

    public HashTag of(String title, QuestionBoard questionBoard){
        return HashTag.builder()
                .title(title)
                .questionBoard(questionBoard)
                .build();
    }
}
