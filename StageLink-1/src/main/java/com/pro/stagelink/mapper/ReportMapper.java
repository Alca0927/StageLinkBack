package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Report;
import com.pro.stagelink.dto.ReportDTO;

import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportDTO toDto(Report report) {
    	ReportDTO dto = new ReportDTO();
        dto.setReportNo(report.getReportNo());
        dto.setPostNo(report.getPostNo());
        dto.setSuspectId(report.getSuspectId());
        dto.setReporterId(report.getReporterId());
        dto.setReportDate(report.getReportDate());
        dto.setReportReason(report.getReportReason());
        return dto;
    }
}
