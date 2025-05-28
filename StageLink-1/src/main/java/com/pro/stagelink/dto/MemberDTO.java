package com.pro.stagelink.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.pro.stagelink.entity.Gender;
import com.pro.stagelink.entity.MemberState;
import com.pro.stagelink.entity.SignupType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
	private Long memberNo;       // 회원 번호 (기본키)
    private String userId;     // 회원 ID
    private String password;   // password
    private String name;   // 이름
    private LocalDate birthDay;   // 생년월일
    private Gender gender;   // 성별
    private String nickname;   // 별명
    private String userEmail;     // 이메일
    private MemberState memberState;      // 상태
    private LocalDate joinedDate;   // 가입일자 (yyyy-mm-dd hh:mm:ss)
    private SignupType signupType;      // 회원가입 유형
}

