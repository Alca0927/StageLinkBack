package com.pro.stagelink.service;

import com.pro.stagelink.domain.Report;
import com.pro.stagelink.dto.ReportDTO;
import com.pro.stagelink.mapper.ReportMapper;
import com.pro.stagelink.repository.ReportRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    // 전체 조회 또는 신고 사유로 검색
    public Page<ReportDTO> getReports(String reason, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10); // 페이지는 0부터 시작

        Page<Report> result;
        if (reason == null || reason.isBlank()) {
            result = reportRepository.findAll(pageable);
        } else {
            result = reportRepository.findByReportReasonContaining(reason, pageable);
        }

        return result.map(reportMapper::toDto);
    }

    // 총 신고 건수
    public long getCount() {
        return reportRepository.count();
    }

    // 상세 조회
    public Optional<ReportDTO> getDetail(int reportNo) {
        return reportRepository.findById(reportNo).map(reportMapper::toDto);
    }
}
