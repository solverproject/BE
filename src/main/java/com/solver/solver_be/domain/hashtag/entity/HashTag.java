package com.solver.solver_be.domain.hashtag.entity;

import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
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
    private HashTag(QuestionBoard questionBoard, String title) {
        this.title = title;
        this.questionBoard = questionBoard;
    }

    public static HashTag of(QuestionBoard questionBoard, String title) {
        return HashTag.builder()
                .title(title)
                .questionBoard(questionBoard)
                .build();
    }
}
