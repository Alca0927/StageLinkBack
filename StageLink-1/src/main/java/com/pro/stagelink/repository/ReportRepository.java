package com.pro.stagelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
