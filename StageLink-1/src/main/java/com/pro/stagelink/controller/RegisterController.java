package com.pro.stagelink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.RefundDTO;
import com.pro.stagelink.dto.ReservationDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.service.RegisterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class RegisterController {
	private final RegisterService registerService;
	
	// 예매
	@GetMapping("/registermanager/reservation/list")
	public PageResponseDTO<ReservationDTO> reservationList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return registerService.reservationList(pageRequestDTO);
	}
	
	@GetMapping("/registermanager/reservation/{reservationNo}")
	public ReservationDTO getShow(@PathVariable(name="reservationNo") int reservationNo) {
		return registerService.getReservation(reservationNo);
	}
	
	
	// 환불
	@GetMapping("/registermanager/refund/list")
	public PageResponseDTO<RefundDTO> refundList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return registerService.refundList(pageRequestDTO);
	}
	
	@GetMapping("/registermanager/refund/{refundNo}")
	public RefundDTO getRefund(@PathVariable(name="refundNo") int refundNo) {
		return registerService.getRefund(refundNo);
	}
	
	// 예매 수 + 환불 수 반환
    @GetMapping("/registermanager")
    public ResponseEntity<Map<String, Integer>> getBookingSummary() {
        int registerCount = (int) registerService.getReservationTotalCount();
        int refundCount = (int) registerService.getRefundTotalCount();

        Map<String, Integer> summary = new HashMap<>();
        summary.put("bookingCount", registerCount);
        summary.put("refundCount", refundCount);

        return ResponseEntity.ok(summary);
    }
}
