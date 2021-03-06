package com.example.gg42.domain.member;

import com.example.gg42.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    // TODO: 이미지 처리 방식 (URL, or anything else)
    private String picture;

    @Builder
    public Member(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public void Update(String picture) {
        this.picture = picture;
    }
}
