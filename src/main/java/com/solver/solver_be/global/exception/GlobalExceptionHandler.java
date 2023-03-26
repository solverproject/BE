package com.solver.solver_be.global.exception;

import com.solver.solver_be.global.exception.exceptionType.*;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // UserException Handler
    @ExceptionHandler(UserException.class)
    public ResponseEntity<GlobalResponseDto> handleUserException(UserException e) {
        ResponseCode responseCode = e.getStatusCode();
        log.error(responseCode.getMessage());
        return ResponseEntity.badRequest()
                .body(GlobalResponseDto.of(responseCode));
    }
    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<GlobalResponseDto> handleCompanyException(CompanyException e) {
        ResponseCode responseCode = e.getStatusCode();
        log.error(responseCode.getMessage());
        return ResponseEntity.badRequest()
                .body(GlobalResponseDto.of(responseCode));
    }
    @ExceptionHandler(VisitFormException.class)
    public ResponseEntity<GlobalResponseDto> handleVisitFormException(VisitFormException e) {
        ResponseCode responseCode = e.getStatusCode();
        log.error(responseCode.getMessage());
        return ResponseEntity.badRequest()
                .body(GlobalResponseDto.of(responseCode));
    }

    // GlobalException Handler
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalResponseDto> handleGlobalException(GlobalException e) {
        ResponseCode responseCode = e.getStatusCode();
        log.error(responseCode.getMessage());
        return ResponseEntity.badRequest()
                .body(GlobalResponseDto.of(responseCode));
    }

    // Validation Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto> handleMethodException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(message);
        return ResponseEntity.badRequest()
                .body(new GlobalResponseDto(HttpStatus.BAD_REQUEST.value(), message, null));
    }
}