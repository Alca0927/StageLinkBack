package com.pro.stagelink.domain;


import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_actor_show")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorShow {
	@EmbeddedId
	private ActorShowId id; // 복합키 클래스 (배우 번호 + 공연 번호)


	@MapsId("actorNo") // 복합키의 actorNo 필드와 매핑
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTOR_NO")
	private Actor actor; // 참조 배우 (TBL_ACTOR FK)
	

	@MapsId("showInfo") // 복합키의 showInfo 필드와 매핑
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHOW_INFO")
	private ShowInfo showInfo; // 참조 공연 정보 (TBL_SHOWINFO FK)


	@Column(name = "ROLE_NAME", length = 50)
	private String roleName; // 배역 이름 (예: '햄릿', '엘파바')


	@Column(name = "SHOW_START_TIME")
	private LocalDate showStartTime; // 배우가 공연에 참여한 시작일


	@Column(name = "SHOW_END_TIME")
	private LocalDate showEndTime; // 배우가 공연에 참여한 종료일
	
	 public void changeShowInfo(ShowInfo showInfo) {
		 this.showInfo = showInfo;
	 }
	 
	 public void changeRoleName(String roleName) {
		 this.roleName = roleName;
	 }
	 
	 public void changeShowStartTime(LocalDate showStartTime) {
		 this.showStartTime = showStartTime;
	 }
	 
	 public void changeShowEndTime(LocalDate showEndTime) {
		 this.showEndTime = showEndTime;
	 } 
}
