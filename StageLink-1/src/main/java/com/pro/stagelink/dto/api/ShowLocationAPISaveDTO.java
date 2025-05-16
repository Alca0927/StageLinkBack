package com.pro.stagelink.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowLocationAPISaveDTO {
	private String showlocation;
    private String locationAddress;
    private String facilityId;
}
