package com.pro.stagelink.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ActorShowDTO {
	private ActorDTO actorDTO;
	private ShowInfoDTO showInfoDTO;
	private String roleName;
	private LocalDate showStartTime;
	private LocalDate showEndTime;
}
