package com.solver.solver_be.global.exception.exceptionType;

import com.solver.solver_be.global.response.ResponseCode;

public class QuestionBoardException extends GlobalException{
    public QuestionBoardException(ResponseCode statusCode){
        super(statusCode);
    }
}