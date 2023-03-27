package com.solver.solver_be.domain.user.dto;

import com.solver.solver_be.domain.user.entity.Admin;
import com.solver.solver_be.domain.user.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String companyName;
    private String name;
    private String phoneNum;

    public static LoginResponseDto of(Admin admin) {
        return LoginResponseDto.builder()
                .name(admin.getName())
                .phoneNum(admin.getPhoneNum())
                .build();
    }

    public static LoginResponseDto of(Guest guest) {
        return LoginResponseDto.builder()
                .name(guest.getName())
                .phoneNum(guest.getPhoneNum())
                .build();
    }

}
