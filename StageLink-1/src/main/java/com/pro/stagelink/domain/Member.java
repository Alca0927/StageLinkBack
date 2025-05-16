package com.pro.stagelink.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@Id
	private int memberNo;       // 회원 번호 (기본키)
	private String memberId;     // 회원 ID
	private String password;   // password
	private String name;   // 이름
	private String birthDay;   // 생년월일
	private String postNo;   // 우편번호
	private String address;   // 주소
	private String gender;   // 성별
	private String nickname;   // 별명
	private String userEmail;     // 이메일
	private String memberState;      // 상태

}