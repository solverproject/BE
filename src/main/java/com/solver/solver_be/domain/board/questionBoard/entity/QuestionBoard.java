package com.solver.solver_be.domain.board.questionBoard.entity;

import com.solver.solver_be.domain.board.questionBoard.dto.QuestionRequestDto;
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
public class QuestionBoard extends TimeStamped {

    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column
    private Long parentBoardId;

    @Column
    private Long x;

    @Column
    private Long y;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private QuestionBoard(Long myBoardId, Long parentBoardId, String title, String contents, Long x, Long y, User user) {
        this.title = title;
        this.contents = contents;
        this.parentBoardId = parentBoardId;
        this.id = myBoardId;
        this.user = user;
        this.x = x;
        this.y = y;
    }

    // 이 부분을 나중에 QuestionBoardDto 로 받는 부분으로 고치면 좋을 듯 합니다.
    public static QuestionBoard of(QuestionRequestDto questionRequestDto, User user) {
        return builder()
                .title(questionRequestDto.getTitle())
                .contents(questionRequestDto.getContents())
                .parentBoardId(questionRequestDto.getParentBoardId())
                .myBoardId(questionRequestDto.getMyBoardId())
                .x(questionRequestDto.getX())
                .y(questionRequestDto.getY())
                .user(user)
                .build();
    }

    public void update(QuestionRequestDto questionRequestDto) {
        this.contents = questionRequestDto.getContents();
    }

}
