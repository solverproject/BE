package com.solver.solver_be.global.exception.exceptionType;

import com.solver.solver_be.global.response.ResponseCode;

public class WorkSpaceException extends GlobalException{
    public WorkSpaceException(ResponseCode statusCode) {
        super(statusCode);
    }

}
