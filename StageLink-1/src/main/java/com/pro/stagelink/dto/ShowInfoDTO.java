package com.pro.stagelink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowInfoDTO {
	private int showInfo;
    private String showPoster;
    private String showName;
    private String showExplain;
    private String showCategory;
    private int showAge;
    private String showDuration;
    private ShowLocationDTO showLocationDTO;
    private String showStyUrl1;
    private String showStyUrl2;
    private String showStyUrl3;
    private String showStyUrl4;
}
