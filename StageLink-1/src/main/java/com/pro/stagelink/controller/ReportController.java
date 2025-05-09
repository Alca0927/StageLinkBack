package com.pro.stagelink.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.domain.Report;
import com.pro.stagelink.dto.ReportDTO;
import com.pro.stagelink.mapper.ReportMapper;
import com.pro.stagelink.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final ReportMapper reportMapper;

    @GetMapping
    public Page<ReportDTO> getReports(
            @RequestParam int page,
            @RequestParam int size) {
        return reportService.getReportList(page, size);
    }

    @GetMapping("/{reportNo}")
    public Report getReportDetail(@PathVariable Long reportNo) {
        return reportService.getReportDetail(reportNo);
    }
}
