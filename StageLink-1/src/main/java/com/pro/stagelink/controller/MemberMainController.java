package com.pro.stagelink.controller;

import com.pro.stagelink.service.MemberService;
import com.pro.stagelink.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/membermanager")
public class MemberMainController {

    private final MemberService memberService;
    private final ReportService reportService;

    public MemberMainController(MemberService memberService, ReportService reportService) {
        this.memberService = memberService;
        this.reportService = reportService;
    }

    // 요약 데이터 반환 (회원 수 + 신고 수)
    @GetMapping
    public ResponseEntity<Map<String, Integer>> getMemberSummary() {
        int memberCount = (int) memberService.getTotalCount();
        int reportCount = (int) reportService.getCount();

        Map<String, Integer> summary = new HashMap<>();
        summary.put("memberCount", memberCount);
        summary.put("reportCount", reportCount);

        return ResponseEntity.ok(summary);
    }
}
