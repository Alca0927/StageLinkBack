package com.pro.stagelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pro.stagelink.domain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
