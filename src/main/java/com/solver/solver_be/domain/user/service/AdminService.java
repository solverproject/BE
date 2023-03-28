package com.solver.solver_be.domain.user.service;

import com.solver.solver_be.domain.company.entity.Company;
import com.solver.solver_be.domain.company.repository.CompanyRepository;
import com.solver.solver_be.domain.user.dto.AdminSignupRequestDto;
import com.solver.solver_be.domain.user.dto.LoginRequestDto;
import com.solver.solver_be.domain.user.dto.LoginResponseDto;
import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import com.solver.solver_be.domain.user.repository.AdminRepository;
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
public class AdminService {

    private static final String COMPANY_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"; // 나중에 발급해야 하므로 이를 발급하는 코드도 필요함.
    private final RefreshTokenRepository refreshTokenRepository;
    private final CompanyRepository companyRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 1. Admin SignUp
    @Transactional
    public ResponseEntity<GlobalResponseDto> signupBusiness(AdminSignupRequestDto signupRequestDto) {

        // Password Encode
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // Duplicated User Check
        Optional<Admin> found = adminRepository.findByUserId(signupRequestDto.getUserId());
        if (found.isPresent()) {
            throw new UserException(ResponseCode.USER_ID_EXIST);
        }

        Optional<Company> foundCompany = companyRepository.findByCompanyTokenAndCompanyName(signupRequestDto.getCompanyToken(), signupRequestDto.getCompanyName());

        // CompanyToken Check
        if (!foundCompany.isPresent()) {
            throw new UserException(ResponseCode.INVALID_COMPANY_TOKEN);
        }
        Company company = companyRepository.findByCompanyToken(signupRequestDto.getCompanyToken());
        UserRoleEnum role = UserRoleEnum.ADMIN;

        // Save Admin Entity
        adminRepository.save(Admin.of(signupRequestDto, password, role, company));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.SIGN_UP_SUCCESS));
    }

    // 2. Admin Login
    @Transactional
    public ResponseEntity<GlobalResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        // User Existed Check
        if (adminRepository.findByUserId(loginRequestDto.getUserId()).isEmpty()) {
            throw new UserException(ResponseCode.USER_ACCOUNT_NOT_EXIST);
        }

        // Password Decode Check
        Admin admin = adminRepository.findByUserId(loginRequestDto.getUserId()).get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), admin.getPassword())) {
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

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.LOG_IN_SUCCESS, LoginResponseDto.of(admin)));
    }
}
