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
	private int show_location;
	private String location_name;
	private String location_address;
	private String facilityId;
}
