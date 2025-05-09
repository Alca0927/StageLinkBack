package com.pro.stagelink.util;

import com.pro.stagelink.domain.ShowInfo;
import com.pro.stagelink.domain.ShowLocation;
import com.pro.stagelink.dto.api.ShowInfoAPISaveDTO;
import com.pro.stagelink.dto.api.ShowLocationAPISaveDTO;


public class DTOConverter {
	public static ShowLocation toEntity(ShowLocationAPISaveDTO dto) {
        return ShowLocation.builder()
        		.locationName(dto.getLocationName())
                .locationAddress(dto.getLocationAddress())
                .build();
    }

    public static ShowInfo toEntity(ShowInfoAPISaveDTO dto, ShowLocation location) {
        return ShowInfo.builder()
                .showPoster(dto.getShowPoster())
                .showName(dto.getShowName())
                .showExplain(dto.getShowExplain())
                .showCategory(dto.getShowCategory())
                .showAge(dto.getShowAge())
                .showDuration(dto.getShowDuration())
                .showLocation(location)
                .showStyUrl1(dto.getShowStyUrl1())
                .showStyUrl2(dto.getShowStyUrl2())
                .showStyUrl3(dto.getShowStyUrl3())
                .showStyUrl4(dto.getShowStyUrl4())
                .build();
    }
}
