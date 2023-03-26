package com.solver.solver_be.domain.business.entity;

import com.solver.solver_be.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String businessNum;

    @Column
    private String businessCode;

    @Column
    private String businessName;

    @Column
    private Integer x;

    @Column
    private Integer y;

    @Builder
    public Business(String businessNum, String businessCode, String businessName
                    , Integer x, Integer y){
        this.businessNum = businessNum;
        this.businessCode = businessCode;
        this.businessName = businessName;
        this.x = x;
        this.y = y;
    }

    public static Business of(String businessNum, String businessCode, String businessName
            , Integer x, Integer y){
        return Business.builder()
                .businessNum(businessNum)
                .businessCode(businessCode)
                .businessName(businessName)
                .build();
    }
}
