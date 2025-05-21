package com.pro.stagelink.repository;

import com.pro.stagelink.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    // 신고 사유로 부분 검색
    Page<Report> findByReportReasonContaining(String reportReason, Pageable pageable);
}
