package com.solver.solver_be.domain.user.service;

import com.solver.solver_be.domain.user.dto.SignupRequestDto;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import com.solver.solver_be.domain.user.repository.UserRepository;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //회원 가입
    @Transactional
    public ResponseEntity<GlobalResponseDto> signup(SignupRequestDto signupRequestDto){
        String userEmail = signupRequestDto.getUserEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();

        //회원 중복 확인
        Optional<User> found = userRepository.findByUserEmail(userEmail);
        if(found.isPresent()) {
            throw new UserException(ResponseCode.USER_EMAIL_EXIST);
        }

        //닉네임 중복 확인
        Optional<User> nickNameFound = userRepository.findByNickname(nickname);
        if (nickNameFound.isPresent()){
            throw new UserException(ResponseCode.USER_NICKNAME_EXIST);
        }

        // 사용자 권한
        UserRoleEnum role = UserRoleEnum.USER;

        User user = User.of(userEmail, password, role, nickname);
        userRepository.save(user);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.SIGN_UP_SUCCESS));
    }
}
