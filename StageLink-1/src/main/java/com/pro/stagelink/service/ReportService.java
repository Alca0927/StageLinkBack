package com.pro.stagelink.service;

import com.pro.stagelink.domain.Report;
import com.pro.stagelink.dto.ReportDTO;
import com.pro.stagelink.mapper.ReportMapper;
import com.pro.stagelink.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    public Page<ReportDTO> getReportList(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("reportNo").descending());
        Page<Report> reportPage = reportRepository.findByReportReasonContainingIgnoreCase(keyword, pageable);
        return reportPage.map(reportMapper::toDto);
    }

    public Optional<ReportDTO> getReportDetail(int reportNo) {
        return reportRepository.findById(reportNo)
                .map(reportMapper::toDto);
    }
}
