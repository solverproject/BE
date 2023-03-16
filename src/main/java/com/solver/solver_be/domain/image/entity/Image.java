package com.solver.solver_be.domain.image.entity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadPath;

    @ManyToOne
    @JoinColumn(name = "QUESTIONBOARD_ID", nullable = false)
    private QuestionBoard questionBoard;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private Image(String uploadPath, User user, QuestionBoard questionBoard) {
        this.uploadPath = uploadPath;
        this.user = user;
        this.questionBoard = questionBoard;
    }

    public static Image of(String uploadPath, User user, QuestionBoard questionBoard) {
        return Image.builder()
                .uploadPath(uploadPath)
                .user(user)
                .questionBoard(questionBoard)
                .build();
    }

}