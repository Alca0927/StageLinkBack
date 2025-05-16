package com.pro.stagelink.repository;

import com.pro.stagelink.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    Page<Report> findByReportReasonContainingIgnoreCase(String reportReason, Pageable pageable);
}

