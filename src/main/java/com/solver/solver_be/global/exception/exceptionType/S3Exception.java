package com.solver.solver_be.global.exception.exceptionType;

import com.solver.solver_be.global.response.ResponseCode;

public class S3Exception extends GlobalException{
    public S3Exception(ResponseCode statusCode){
        super(statusCode);
    }
}