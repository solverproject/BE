package com.solver.solver_be.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.solver_be.domain.user.dto.SocialLoginRequestDto;
import com.solver.solver_be.domain.user.dto.SocialLoginVendor;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.user.entity.UserRoleEnum;
import com.solver.solver_be.domain.user.repository.UserRepository;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import com.solver.solver_be.global.security.jwt.JwtUtil;
import com.solver.solver_be.global.security.refreshtoken.RefreshToken;
import com.solver.solver_be.global.security.refreshtoken.RefreshTokenRepository;
import com.solver.solver_be.global.security.refreshtoken.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final SocialLoginVendor socialLoginVendor;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public ResponseEntity<GlobalResponseDto> socialLogin(String vendor, String code, String state, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(vendor, code, state);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        SocialLoginRequestDto socialUserInfo = getSocialUserInfo(vendor, accessToken);

        // 3. 필요시에 회원가입
        User socialUser = registerSocialUserIfNeeded(vendor, socialUserInfo);

        // 4. JWT 토큰 반환
        TokenDto tokenDto = jwtUtil.createAllToken(socialUser.getUserEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findAllByUserEmail(socialUser.getUserEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), socialUser.getUserEmail());
            refreshTokenRepository.save(newToken);
        }
        jwtUtil.setHeader(response, tokenDto);

        String token = jwtUtil.createToken(socialUser.getUserEmail(), "Access");

        Cookie cookie = new Cookie("token", token.substring(7));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.LOG_IN_SUCCESS));

    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String vendor, String code, String state) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        Map<String, String> switchVendor = switchVendor(vendor);

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", switchVendor.get("clientId"));
        body.add("redirect_uri", switchVendor.get("redirectUri"));
//        body.add("client_secret", switchVendor.get("clientSecret"));
        body.add("code", code);
//        body.add("state", state);

//        String tokenUri = switchVendor.get("tokenUri");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> socialTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity <String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                socialTokenRequest,
                String.class
        );


        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private SocialLoginRequestDto getSocialUserInfo(String vendor, String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> socialUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                switchVendor(vendor).get("userInfoUri"),
                HttpMethod.POST,
                socialUserInfoRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String id = "";
        String email = "";
        String nickname = "";
        switch (vendor) {
            case "kakao":
                id = jsonNode.get("id").asText();
                email = jsonNode.get("kakao_account").get("email").asText();
                nickname = jsonNode.get("properties").get("nickname").asText();
                break;
//            case "google":
//                id = jsonNode.get("exp").asText();
//                email = jsonNode.get("email").asText();
//                nickname = jsonNode.get("nickname").asText();
//                break;
//            case "naver":
//                id = String.valueOf(jsonNode.get("response").get("id"));
//                email = jsonNode.get("response").get("email").asText();
//                nickname = jsonNode.get("response").get("nickname").asText();
//                break;
        }
        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new SocialLoginRequestDto(id, nickname, email);
    }

    // 3. 필요시에 회원가입
    private User registerSocialUserIfNeeded(String vendor, SocialLoginRequestDto socialUserInfo) {

        // DB 에 중복된 Kakao Id 가 있는지 확인
        String socialId = socialUserInfo.getId();

        User socialUser = null;
        switch (vendor) {
            case "kakao":
                socialUser = userRepository.findByKakaoId(Long.valueOf(socialId)).orElse(null);
                break;
//            case "google":
//                socialUser = userRepository.findByGoogleId(Long.valueOf(socialId)).orElse(null);
//                break;
//            case "naver":
//                socialUser = userRepository.findByNaverId(socialId).orElse(null);
//                break;
//            default:
//                break;
        }

        if (socialUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String socialEmail = socialUserInfo.getEmail();
            User sameEmailUser = userRepository.findByUserEmail(socialEmail).orElse(null);

            if (sameEmailUser != null) {
                socialUser = sameEmailUser;

                // 기존 회원정보에 카카오 Id 추가
                switch (vendor) {
                    case "kakao":
                        socialUser = socialUser.kakaoIdUpdate(Long.valueOf(socialId));
                        break;
//                    case "google":
//                        socialUser = socialUser.googleIdUpdate(Long.valueOf(socialId));
//                        break;
//                    case "naver":
//                        socialUser = socialUser.naverIdUpdate(socialId);
//                        break;
                }
                socialUser = userRepository.save(socialUser);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String userEmail = socialUserInfo.getEmail();

                socialUser = User.of(userEmail, encodedPassword, UserRoleEnum.USER, socialUserInfo.getNickname());
                switch (vendor) {
                    case "kakao":
                        socialUser = socialUser.kakaoIdUpdate(Long.valueOf(socialId));
                        break;
//                    case "google":
//                        socialUser = socialUser.googleIdUpdate(Long.valueOf(socialId));
//                        break;
//                    case "naver":
//                        socialUser = socialUser.naverIdUpdate(socialId);
//                        break;
                }
                userRepository.save(socialUser);
            }
        }
        return socialUser;
    }

    public Map<String, String> switchVendor(String vendor) {

        Map<String, String> socialUserMap = new HashMap<>();
        String clientId = "";
        String redirectUri = "";
//        String clientSecret = "";
        String userInfoUri = "";
        String tokenUri = "";

        switch (vendor) {
            case "kakao":
                clientId = socialLoginVendor.getKakaoClientId();
                redirectUri = socialLoginVendor.getKakaoRedirectUri();
                userInfoUri = socialLoginVendor.getKakaoUserInfoUri();
                tokenUri = socialLoginVendor.getKakaoTokenUri();
                break;
//            case "google":
//                clientId = socialLoginVendor.getGoogleClientId();
//                redirectUri = socialLoginVendor.getGoogleRedirectUri();
//                userInfoUri = socialLoginVendor.getGoogleUserInfoUri();
//                tokenUri = socialLoginVendor.getGoogleTokenUri();
//                clientSecret = socialLoginVendor.getGoogleClientSecret();
//                break;
//            case "naver":
//                clientId = socialLoginVendor.getNaverClientId();
//                redirectUri = socialLoginVendor.getNaverRedirectUri();
//                userInfoUri = socialLoginVendor.getNaverUserInfoUri();
//                tokenUri = socialLoginVendor.getNaverTokenUri();
//                clientSecret = socialLoginVendor.getNaverClientSecret();
//                break;
        }

        socialUserMap.put("clientId", clientId);
        socialUserMap.put("redirectUri", redirectUri);
        socialUserMap.put("userInfoUri", userInfoUri);
        socialUserMap.put("tokenUri", tokenUri);
//        socialUserMap.put("clientSecret", clientSecret);
        return socialUserMap;
    }
}