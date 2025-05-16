package com.pro.stagelink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatDTO {
    private int seatId;
    private String seatClass;
    private int seatNumber;
    private int seatReserved = 0;
    private ShowDTO showDTO;
}
