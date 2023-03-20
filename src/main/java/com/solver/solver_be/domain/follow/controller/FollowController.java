package com.solver.solver_be.domain.follow.controller;

import com.solver.solver_be.domain.follow.service.FollowService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    @PutMapping("/users/follow/{id}")
    public ResponseEntity<GlobalResponseDto> createFollow(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.createFollow(id, userDetails.getUser());
    }

}