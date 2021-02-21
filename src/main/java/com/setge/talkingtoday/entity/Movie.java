package com.setge.talkingtoday.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
@Entity
public class Movie extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY) // member 테이블이 필요한 경우에 가져온다.
    private Member member;

}
