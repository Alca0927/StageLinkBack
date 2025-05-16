package com.pro.stagelink.repository;

import com.pro.stagelink.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Query("SELECT COUNT(n) FROM Notice n")
    long countNotices();
}