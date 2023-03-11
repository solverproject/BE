package com.solver.solver_be.domain.hashtag.dto;

import com.solver.solver_be.domain.hashtag.entity.HashTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashTagResponseDto {

    private Long id;
    private String title;

    @Builder
    private HashTagResponseDto(HashTag hashTag){
        this.id = hashTag.getId();
        this.title = hashTag.getTitle();
    }

    public static HashTagResponseDto of(HashTag hashTag){
        return HashTagResponseDto.builder()
                .hashTag(hashTag)
                .build();
    }
}
