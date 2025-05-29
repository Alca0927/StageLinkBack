package com.pro.stagelink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesStatDTO {
	private int id;
    private int salesSum;
    private int year;
    private int month;
    private LocalDateTime createdDate;
}