package com.pro.stagelink.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowInfoAPISaveDTO {
	private String showPoster;
    private String showName;
    private String showExplain;
    private String showCategory;
    private int showAge;
    private String showDuration;
    private ShowLocationAPISaveDTO showLocationDTO;
    private String showStyUrl1;
    private String showStyUrl2;
    private String showStyUrl3;
    private String showStyUrl4;
}
