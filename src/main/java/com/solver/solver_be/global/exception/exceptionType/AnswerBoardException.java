package com.solver.solver_be.global.exception.exceptionType;

import com.solver.solver_be.global.response.ResponseCode;

public class AnswerBoardException extends GlobalException{
    public AnswerBoardException(ResponseCode statusCode) {
        super(statusCode);
    }
}
