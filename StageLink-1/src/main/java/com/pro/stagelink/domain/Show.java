       package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_SHOW")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "SHOW_NO")
	 private int showNo;

	 // 공연 정보 FK
	 @ManyToOne
	 @JoinColumn(name = "SHOW_INFO")
	 private ShowInfo showInfo;

	 @Column(name = "SHOW_START_TIME")
	 private LocalDateTime showStartTime;

	 @Column(name = "SHOW_END_TIME")
	 private LocalDateTime showEndTime;

	 @Column(name = "SEAT_R_PRICE")
	 private int seatRPrice;

	 @Column(name = "SEAT_A_PRICE")
	 private int seatAPrice;

	 @Column(name = "SEAT_S_PRICE")
	 private int seatSPrice;

	 @Column(name = "SEAT_VIP_PRICE")
	 private int seatVipPrice;

	 @Column(name = "SEAT_R_COUNT")
	 private int seatRCount;

	 @Column(name = "SEAT_A_COUNT")
	 private int seatACount;

	 @Column(name = "SEAT_S_COUNT")
	 private int seatSCount;
	
	 @Column(name = "SEAT_VIP_COUNT")
	 private int seatVipCount;

	 @Column(name = "SHOW_STATE")
	 private int showState;
	
	 public void changeShowInfo(ShowInfo showInfo) {
		 this.showInfo = showInfo;
	 }
	 
	 public void changeShowStartTime(LocalDateTime showStartTime) {
		 this.showStartTime = showStartTime;
	 }
	 
	 public void changeShowEndTime(LocalDateTime showEndTime) {
		 this.showEndTime = showEndTime;
	 }
	 
	 public void changeSeatRPrice(int seatRPrice) {
		 this.seatRPrice = seatRPrice;
	 }
	 
	 public void changeSeatAPrice(int seatAPrice) {
		 this.seatAPrice = seatAPrice;
	 }
	 
	 public void changeSeatSPrice(int seatSPrice) {
		 this.seatSPrice = seatSPrice;
	 }
	 
	 public void changeSeatVipPrice(int seatVipPrice) {
		 this.seatVipPrice = seatVipPrice;
	 }
	 
	 public void changeSeatRCount(int seatRCount) {
		 this.seatRCount = seatRCount;
	 }
	 
	 public void changeSeatACount(int seatACount) {
		 this.seatACount = seatACount;
	 }
	 
	 public void changeSeatSCount(int seatSCount) {
		 this.seatSCount = seatSCount;
	 }
	 
	 public void changeSeatVipCount(int seatVipCount) {
		 this.seatVipCount = seatVipCount;
	 }
	 
	 public void changeShowState(int showState) {
		 this.showState = showState;
	 }
	 
}
