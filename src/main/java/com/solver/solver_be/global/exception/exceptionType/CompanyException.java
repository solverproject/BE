package com.solver.solver_be.global.exception.exceptionType;

import com.solver.solver_be.global.response.ResponseCode;

public class CompanyException extends GlobalException{

    public CompanyException(ResponseCode statusCode) {
        super(statusCode);
    }
}
