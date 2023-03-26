package com.solver.solver_be.domain.visitform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class VisitFormRequestDto {

    @NotBlank(message = "방문 회사 입력은 필수입니다.")
    private String location;

    @NotBlank(message = "방문할 장소 입력은 필수입니다.")
    private String place;

    @NotBlank(message = "찾아갈 분의 입력은 필수입니다.")
    private String target;

    @NotBlank(message = "방문 목적은 필수입니다.")
    private String purpose;

    @NotBlank(message = "방문 날짜를 등록 해주세요.")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "방문 날짜 입력은 yyyy-mm-dd 형식 입니다.")
    private String startDate;

    @NotBlank(message = "방문 시간을 등록 해주세요.")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9])$", message = "방문 시간 입력은 hh:mm 형식 입니다.")
    private String startTime;

    @NotBlank(message = "종료 날짜를 등록 해주세요.")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "종료 날짜 입력은 yyyy-mm-dd 형식 입니다.")
    private String endDate;

    @NotBlank(message = "종료 시간을 등록 해주세요.")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9])$", message = "종료 시간 입력은 hh:mm 형식 입니다.")
    private String endTime;

    @NotBlank
    private String status;
}
