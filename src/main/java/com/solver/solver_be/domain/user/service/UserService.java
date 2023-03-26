package com.solver.solver_be.domain.user.service;

import com.solver.solver_be.domain.user.dto.BusinessSignupRequestDto;
import com.solver.solver_be.domain.user.dto.GuestSignupRequestDto;
import com.solver.solver_be.domain.user.dto.LoginRequestDto;
import com.solver.solver_be.domain.user.dto.LoginResponseDto;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import com.solver.solver_be.domain.user.repository.UserRepository;
import com.solver.solver_be.global.exception.exceptionType.UserException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import com.solver.solver_be.global.security.jwt.JwtUtil;
import com.solver.solver_be.global.security.refreshtoken.RefreshToken;
import com.solver.solver_be.global.security.refreshtoken.RefreshTokenRepository;
import com.solver.solver_be.global.security.refreshtoken.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String COMPANY_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"; // 나중에 발급해야 하므로 이를 발급하는 코드도 필요함.
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Admin 회원 가입
    @Transactional
    public ResponseEntity<GlobalResponseDto> signupBusiness(BusinessSignupRequestDto signupRequestDto) {

        String userId = signupRequestDto.getUserId();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUserId(userId);
        if (found.isPresent()) {
            throw new UserException(ResponseCode.USER_ID_EXIST);
        }

        // 사용자 권한
        if (!signupRequestDto.getCompanyToken().equals(COMPANY_TOKEN)) {
            throw new UserException(ResponseCode.INVALID_COMPANY_TOKEN);
        }
        UserRoleEnum role = UserRoleEnum.ADMIN;

        // 객체 생성 및 저장.
        User user = User.of(signupRequestDto, userId, password, role);
        userRepository.save(user);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.SIGN_UP_SUCCESS, user));
    }

    // Guest 회원가입
    @Transactional
    public ResponseEntity<GlobalResponseDto> signupGuest(GuestSignupRequestDto signupRequestDto) {

        String userId = signupRequestDto.getUserId();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUserId(userId);
        if (found.isPresent()) {
            throw new UserException(ResponseCode.USER_ID_EXIST);
        }

        // 사용자 권한
        UserRoleEnum role = UserRoleEnum.GUEST;

        // 객체 생성 및 저장.
        User user = User.of(signupRequestDto, userId, password, role);
        userRepository.save(user);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.SIGN_UP_SUCCESS, user));
    }

    // Admin 로그인
    @Transactional
    public ResponseEntity<GlobalResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        // 유저가 존재하는지 확인.
        if (userRepository.findByUserId(userId).isEmpty()) {
            throw new UserException(ResponseCode.USER_ACCOUNT_NOT_EXIST);
        }

        // 비밀번호를 확인.
        User user = userRepository.findByUserId(userId).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(ResponseCode.PASSWORD_MISMATCH);
        }

        // AccessToken 발행.
        TokenDto tokenDto = jwtUtil.createAllToken(userId);

        // RefreshToken 발행.
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findAllByUserEmail(userId);
        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), userId);
            refreshTokenRepository.save(newToken);
        }
        jwtUtil.setHeader(response, tokenDto);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.LOG_IN_SUCCESS, LoginResponseDto.of(user)));

    }

}
