package com.solver.solver_be.global.exception.exceptionType;

import com.solver.solver_be.global.response.ResponseCode;

public class MindMapException extends GlobalException{
    public MindMapException(ResponseCode statusCode){
        super(statusCode);
    }
}
