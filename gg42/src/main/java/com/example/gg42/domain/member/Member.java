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

    /*
    @Column
    private LocalDateTime deletedDate;
     */

    @Column(nullable = false)
    private String name;

    @Column
    // TODO: 이미지 처리 방식 (URL, or anything else)
    private String picture;

    /*
    @Column(nullable = false)
    private boolean blackhole;
     */

    @Builder
    public Member(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }
}
