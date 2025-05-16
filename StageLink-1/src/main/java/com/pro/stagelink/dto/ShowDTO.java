package com.pro.stagelink.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowDTO {
	private int showNo;
	private ShowInfoDTO showInfoDTO;
	private LocalDateTime showStartTime;
	private LocalDateTime showEndTime;
	private int seatRPrice;
	private int seatAPrice;
	private int seatSPrice;
	private int seatVipPrice;
	private int seatRCount;
	private int seatACount;
	private int seatSCount;
	private int seatVipCount;
	private int showState;
}
