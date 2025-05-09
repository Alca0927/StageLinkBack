package com.pro.stagelink.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pro.stagelink.domain.Report;
import com.pro.stagelink.dto.ReportDTO;
import com.pro.stagelink.mapper.ReportMapper;
import com.pro.stagelink.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public Page<ReportDTO> getReportList(int page, int size) {
        return reportRepository.findAll(PageRequest.of(page, size))
                .map(reportMapper::toDto);
    }

    public Report getReportDetail(Long reportNo) {
        return reportRepository.findById(reportNo)
                .orElseThrow(() -> new RuntimeException("신고 내역 없음"));
    }
}
