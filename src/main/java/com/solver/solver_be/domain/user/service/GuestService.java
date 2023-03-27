package com.solver.solver_be.domain.user.service;

import com.solver.solver_be.domain.user.dto.GuestSignupRequestDto;
import com.solver.solver_be.domain.user.dto.LoginRequestDto;
import com.solver.solver_be.domain.user.dto.LoginResponseDto;
import com.solver.solver_be.domain.user.entity.Guest;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import com.solver.solver_be.domain.user.repository.GuestRepository;
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
public class GuestService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    // Guest 회원가입
    @Transactional
    public ResponseEntity<GlobalResponseDto> signupGuest(GuestSignupRequestDto signupRequestDto) {

        // Password Encode
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // Duplicated User Check
        Optional<Guest> found = guestRepository.findByUserId(signupRequestDto.getUserId());
        if (found.isPresent()) {
            throw new UserException(ResponseCode.USER_ID_EXIST);
        }

        // UserRole Check
        UserRoleEnum role = UserRoleEnum.GUEST;

        // Save Entity
        guestRepository.save(Guest.of(signupRequestDto,password, role));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.SIGN_UP_SUCCESS));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        // User Existed Check
        if (guestRepository.findByUserId(loginRequestDto.getUserId()).isEmpty()) {
            throw new UserException(ResponseCode.USER_ACCOUNT_NOT_EXIST);
        }

        // Password Decode Check
        Guest guest = guestRepository.findByUserId(loginRequestDto.getUserId()).get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), guest.getPassword())) {
            throw new UserException(ResponseCode.PASSWORD_MISMATCH);
        }

        // Granting AccessToken
        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getUserId());

        // Granting RefreshToken
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findAllByUserEmail(loginRequestDto.getUserId());
        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginRequestDto.getUserId());
            refreshTokenRepository.save(newToken);
        }
        jwtUtil.setHeader(response, tokenDto);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.LOG_IN_SUCCESS, LoginResponseDto.of(guest)));
    }


}
