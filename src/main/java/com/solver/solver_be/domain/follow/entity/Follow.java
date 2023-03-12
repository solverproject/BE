package com.solver.solver_be.domain.follow.entity;

import com.solver.solver_be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FOLLOW_ID", nullable = false)
    private User follow;

    @ManyToOne
    @JoinColumn(name = "FOLLOWING_ID", nullable = false)
    private User following;

    @Builder
    private Follow(User follow, User following) {
        this.follow = follow;
        this.following = following;
    }

    public static Follow of(User follow, User following){
        return Follow.builder()
                .follow(follow)
                .following(following)
                .build();
    }
}
