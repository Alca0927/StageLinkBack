package com.pro.stagelink.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    private Long mbrNo;       // 회원 번호 (기본키)
    private String mbrId;     // 회원 ID
    private String mbrName;   // 이름
    private String email;     // 이메일
    private String stat;      // 상태
}

