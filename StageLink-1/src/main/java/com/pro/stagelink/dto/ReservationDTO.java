package com.pro.stagelink.dto;

import java.time.LocalDateTime;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.domain.ReservationStatus;
import com.pro.stagelink.domain.Show;
import com.pro.stagelink.domain.ShowSeat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
	private int reservationNo;
    private Member member; // 회원 정보 (Member Entity 필요)
    private Show show; // 공연 정보 (Show Entity 필요)
    private ShowSeat seat; // 좌석 정보 (ShowSeat Entity 필요)
    private LocalDateTime reservationDate;
    private ReservationStatus status;
}
