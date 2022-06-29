package com.example.gg42.web.dto;

import com.example.gg42.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String name;
    private String photo;

    @Builder
    public MemberSaveRequestDto(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }
}
