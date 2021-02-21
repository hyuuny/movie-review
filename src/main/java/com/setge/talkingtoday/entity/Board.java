package com.setge.talkingtoday.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int viewCnt;

    @ManyToOne(fetch = FetchType.LAZY) // member 테이블이 필요한 경우에 가져온다.
    private Member member;

}
