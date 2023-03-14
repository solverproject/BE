//package com.solver.solver_be.domain.user.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.solver.solver_be.domain.user.dto.KakaoUserInfoDto;
//import com.solver.solver_be.domain.user.entity.User;
//import com.solver.solver_be.domain.user.entity.UserRoleEnum;
//import com.solver.solver_be.domain.user.repository.UserRepository;
//import com.solver.solver_be.global.response.GlobalResponseDto;
//import com.solver.solver_be.global.response.ResponseCode;
//import com.solver.solver_be.global.security.jwt.JwtUtil;
//import com.solver.solver_be.global.security.refreshtoken.RefreshToken;
//import com.solver.solver_be.global.security.refreshtoken.RefreshTokenRepository;
//import com.solver.solver_be.global.security.refreshtoken.TokenDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class KakaoService {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final JwtUtil jwtUtil;
//
//    public ResponseEntity<GlobalResponseDto> kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getToken(code);
//
//        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
//        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
//
//        // 3. 필요시에 회원가입
//        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
//
//        // 4. JWT 토큰 반환
//        TokenDto tokenDto = jwtUtil.createAllToken(kakaoUser.getUserEmail());
//
//        Optional<RefreshToken> refreshToken = refreshTokenRepository.findAllByUserEmail(kakaoUser.getUserEmail());
//
//        if (refreshToken.isPresent()) {
//            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
//        } else {
//            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), kakaoUser.getUserEmail());
//            refreshTokenRepository.save(newToken);
//        }
//        jwtUtil.setHeader(response, tokenDto);
//
//        String token =  jwtUtil.createToken(kakaoUser.getUserEmail(), "Access");
//
//        Cookie cookie = new Cookie("token", token.substring(7));
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(3600);
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.LOG_IN_SUCCESS));
//    }
//
//    // 1. "인가 코드"로 "액세스 토큰" 요청
//    private String getToken(String code) throws JsonProcessingException {
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "69e38f21fc5c4ccd6f0a47a084915c4b");
//        body.add("redirect_uri", "http://localhost:8080/api/users/kakao/callback");
//        body.add("code", code);
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        return jsonNode.get("access_token").asText();
//    }
//
//    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
//    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties")
//                .get("nickname").asText();
//        String email = jsonNode.get("kakao_account")
//                .get("email").asText();
//
//        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
//        return new KakaoUserInfoDto(id, nickname, email);
//    }
//
//    // 3. 필요시에 회원가입
//    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        Long kakaoId = kakaoUserInfo.getId();
//        User kakaoUser = userRepository.findByKakaoId(kakaoId)
//                .orElse(null);
//        if (kakaoUser == null) {
//            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
//            String kakaoEmail = kakaoUserInfo.getUsername();
//            User sameEmailUser = userRepository.findByUserEmail(kakaoEmail).orElse(null);
//            if (sameEmailUser != null) {
//                kakaoUser = sameEmailUser;
//                // 기존 회원정보에 카카오 Id 추가
//                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
//            } else {
//                // 신규 회원가입
//                // password: random UUID
//                String password = UUID.randomUUID().toString();
//                String encodedPassword = passwordEncoder.encode(password);
//
//                // email: kakao email
//                String userEmail = kakaoUserInfo.getUsername();
//
//                kakaoUser = User.of(userEmail, encodedPassword, UserRoleEnum.USER, kakaoUserInfo.getNickname(), kakaoId);
//            }
//
//            userRepository.save(kakaoUser);
//        }
//        return kakaoUser;
//    }
//}