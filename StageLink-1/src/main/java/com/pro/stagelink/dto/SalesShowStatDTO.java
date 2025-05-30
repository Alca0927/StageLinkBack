package com.pro.stagelink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesShowStatDTO {
	private int showNo;
    private String showName;
    private String seatClass;
    private int reservedCount;
    private int seatPrice;
    private int sales;
}
