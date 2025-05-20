package com.pro.stagelink.dto;

import java.time.LocalDateTime;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.domain.Reservation;
import com.pro.stagelink.domain.ShowSeat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
	private int refundNo;
    private Member member; // 회원 정보
    private Reservation reservation; // 예매 정보
    private ShowSeat seat; // 좌석 정보
    private LocalDateTime refundDate;
}