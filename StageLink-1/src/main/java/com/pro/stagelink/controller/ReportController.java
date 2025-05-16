package com.pro.stagelink.controller;

import com.pro.stagelink.dto.ReportDTO;
import com.pro.stagelink.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public Page<ReportDTO> getReports(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return reportService.getReportList(keyword, page, size);
    }

    @GetMapping("/{reportNo}")
    public Optional<ReportDTO> getReportDetail(@PathVariable int reportNo) {
        return reportService.getReportDetail(reportNo);
    }
}
