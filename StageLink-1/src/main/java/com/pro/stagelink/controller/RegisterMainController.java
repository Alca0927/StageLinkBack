package com.pro.stagelink.controller;
import com.pro.stagelink.service.RegisterService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/bookingmanager")
public class RegisterMainController {

    private final RegisterService registerService;

    public RegisterMainController(RegisterService registerService) {
        this.registerService = registerService;
    }

    // 예매 수 + 환불 수 반환
    @GetMapping
    public ResponseEntity<Map<String, Integer>> getBookingSummary() {
        int registerCount = (int) registerService.getReservationTotalCount();
        int refundCount = (int) registerService.getRefundTotalCount();

        Map<String, Integer> summary = new HashMap<>();
        summary.put("bookingCount", registerCount);
        summary.put("refundCount", refundCount);

        return ResponseEntity.ok(summary);
    }
}
