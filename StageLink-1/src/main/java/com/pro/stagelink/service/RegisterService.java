package com.pro.stagelink.service;

import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.RefundDTO;
import com.pro.stagelink.dto.ReservationDTO;

public interface RegisterService {
	// 예매 
	ReservationDTO getReservation(int reservationNo);

	PageResponseDTO<ReservationDTO> reservationList(PageRequestDTO pageRequestDTO);
	
	// 환불
	RefundDTO getRefund(int refundNo);
	
	PageResponseDTO<RefundDTO> refundList(PageRequestDTO pageRequestDTO);
}
