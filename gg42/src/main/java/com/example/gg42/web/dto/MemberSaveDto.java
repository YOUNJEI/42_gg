package com.example.gg42.web.dto;

import com.example.gg42.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveDto {
    private String login;
    private String image_url;

    @Builder
    public MemberSaveDto(String login, String image_url) {
        this.login = login;
        this.image_url = image_url;
    }

    public Member toEntity() {
        return Member.builder()
                .name(login)
                .picture(image_url)
                .build();
    }
}