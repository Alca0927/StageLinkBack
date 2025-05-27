package com.pro.stagelink.entity;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "TBL_MEMBER")
@ToString(exclude = "password")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEMBER", nullable = false) // 회원번호 (PK)
  private Long memberNo;
  
  @Column(name = "ID", length = 50, nullable = false, unique = true)
  private String userId;  // 로그인용 ID
  
  @Column(name = "PASSWORD", nullable = false)
  private String password;
  
  @Column(name = "NAME",length = 10)
  private String name;
  
  @Column(name = "BIRTHDAY")
  private LocalDate birthday;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "GENDER")
  private Gender gender;
  
  @Column(name = "NICKNAME", length = 20, nullable = false)
  private String nickname;
  
  @Column(name = "USER_EMAIL", length = 50, nullable = false, unique = true)
  private String userEmail;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "MEMBER_STATE", nullable = false)
  private MemberState memberState;
  
  @Column(name="JOINED_DATE")
  private Date joinedDate;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "SIGNUP_TYPE")
  private SignupType signpType;
}
