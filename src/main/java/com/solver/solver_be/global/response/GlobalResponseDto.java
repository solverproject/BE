package com.solver.solver_be.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GlobalResponseDto {
    private int statusCode;
    private String message;
    private Object data;

    @Builder
    private GlobalResponseDto(ResponseCode responseCode, Object data) {
        this.statusCode = responseCode.getStatusCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public GlobalResponseDto (int statusCode, String message, Object data){
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static GlobalResponseDto of(ResponseCode responseCode, Object data){
        return GlobalResponseDto.builder()
                .responseCode(responseCode)
                .data(data)
                .build();
    }

    public static GlobalResponseDto of(ResponseCode responseCode){
        return GlobalResponseDto.builder()
                .responseCode(responseCode)
                .build();
    }
}