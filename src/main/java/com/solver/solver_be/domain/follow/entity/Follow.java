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
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "FOLLOWING_ID", nullable = false)
    private User followUser;

    @Builder
    private Follow(User user, User followUser) {
        this.user = user;
        this.followUser = followUser;
    }

    public static Follow of(User user, User followUser) {
        return Follow.builder()
                .user(user)
                .followUser(followUser)
                .build();
    }
}
