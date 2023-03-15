package com.solver.solver_be.domain.follow.controller;

import com.solver.solver_be.domain.follow.service.FollowService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    // 팔로우 등록 및 취소
    @PutMapping("/users/follow/{id}")
    public ResponseEntity<GlobalResponseDto> createFollow (@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.createFollow(id, userDetails.getUser());
    }

}