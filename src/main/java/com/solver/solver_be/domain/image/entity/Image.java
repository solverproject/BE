package com.solver.solver_be.domain.image.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private QuestionBoard questionBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    private Image(String uploadPath, User user, Post post) {
        this.uploadPath = uploadPath;
        this.user = user;
        this.post = post;
    }

    public static Image of(String uploadPath, User user, Post post){
        return Image.builder()
                .uploadPath(uploadPath)
                .user(user)
                .post(post)
                .build();
    }

}