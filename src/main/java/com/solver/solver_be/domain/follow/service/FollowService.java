package com.solver.solver_be.domain.follow.service;

import com.solver.solver_be.domain.follow.entity.Follow;
import com.solver.solver_be.domain.follow.repository.FollowRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.user.repository.UserRepository;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto> createFollow(Long id, User user) {
        User followuser = userRepository.findById(id).orElseThrow(
                () -> new UserException(ResponseCode.USER_NOT_FOUND)
        );
        Optional<Follow> follow = followRepository.findByUserIdAndFollowUserId(user.getId(), followuser.getId());
        if (follow.isEmpty()) {
            followRepository.saveAndFlush(Follow.of(user, followuser));
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.FOLLOW_SUCCESS));
        } else {
            followRepository.deleteByUserIdAndFollowUserId(user.getId(), followuser.getId());
            return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.FOLLOW_CANCEL));
        }

    }
}
