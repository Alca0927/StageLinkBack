package com.pro.stagelink.domain;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
	@Column(name = "member")
	private int memberNo;       // 회원 번호 (기본키)
	
	@Column(name="ID")
	private String memberId;     // 회원 ID
	
	@Column(name = "password")
	private String password;   // password
	
	@Column(name = "name")
	private String name;   // 이름
	
	@Column(name="birthday")
	private String birthDay;   // 생년월일
	
	@Column(name="post_no")
	private String postNo;   // 우편번호
	
	@Column(name="address")
	private String address;   // 주소
	
	@Column(name="Gender")
	private String gender;   // 성별
	
	@Column(name="nickname")
	private String nickname;   // 별명
	
	@Column(name="user_email")
	private String userEmail;     // 이메일
	
	@Column(name="member_state")
	private String memberState;      // 상태

}