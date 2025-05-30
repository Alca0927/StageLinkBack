package com.pro.stagelink.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.ShowInfo;

public interface ShowInfoRepository extends JpaRepository<ShowInfo, Integer> {

    Optional<ShowInfo> findById(int showInfo);

    // ✅ 공연명으로 검색
    Page<ShowInfo> findByShowNameContaining(String keyword, Pageable pageable);
}
