package com.pro.stagelink.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Show;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    // 공연명(ShowInfo의 showName)으로 검색
    Page<Show> findByShowInfo_ShowNameContaining(String keyword, Pageable pageable);
}
