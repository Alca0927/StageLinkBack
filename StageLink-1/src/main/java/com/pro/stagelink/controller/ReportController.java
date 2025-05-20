package com.pro.stagelink.controller;

import com.pro.stagelink.dto.ReportDTO;
import com.pro.stagelink.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 신고 목록 조회 (검색 및 페이징)
    @GetMapping
    public Page<ReportDTO> getReports(
            @RequestParam(name = "reason", required = false) String reason,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return reportService.getReports(reason, page);
    }

    // 신고 건수
    @GetMapping("/count")
    public Map<String, Long> getCount() {
        return Map.of("count", reportService.getCount());
    }

    // 신고 상세 조회
    @GetMapping("/{reportNo}")
    public ReportDTO getDetail(@PathVariable int reportNo) {
        return reportService.getDetail(reportNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고 번호를 찾을 수 없습니다."));
    }
}