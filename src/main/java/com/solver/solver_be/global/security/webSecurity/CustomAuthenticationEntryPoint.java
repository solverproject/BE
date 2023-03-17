package com.solver.solver_be.global.security.webSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseCode exception = (ResponseCode) request.getAttribute("exception");

        if (exception.equals(ResponseCode.NOT_VALID_REQUEST)) {
            exceptionHandler(response, ResponseCode.NOT_VALID_REQUEST);
            return;
        }

        if (exception.equals(ResponseCode.TOKEN_NOT_FOUND)) {
            exceptionHandler(response, ResponseCode.TOKEN_NOT_FOUND);
            return;
        }

        if (exception.equals(ResponseCode.NOT_VALID_TOKEN)) {
            exceptionHandler(response, ResponseCode.NOT_VALID_TOKEN);
            return;
        }
        if (exception.equals(ResponseCode.NOT_VALID_REFRESH_TOKEN)) {
            exceptionHandler(response, ResponseCode.NOT_VALID_REFRESH_TOKEN);
            return;
        }
        if (exception.equals(ResponseCode.USER_NOT_FOUND)) {
            exceptionHandler(response, ResponseCode.USER_NOT_FOUND);
        }
    }

    public void exceptionHandler(HttpServletResponse response, ResponseCode error) {
        response.setStatus(error.getStatusCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(GlobalResponseDto.of(error));
            response.getWriter().write(json);
            log.error(error.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}