package com.pro.stagelink.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowLocationDTO {
	private int showlocation;
	private String locationName;
	private String locationAddress;
	private String facilityId;
}
