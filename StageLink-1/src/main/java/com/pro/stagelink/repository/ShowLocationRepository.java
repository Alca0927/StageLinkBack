package com.pro.stagelink.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.ShowLocation;

public interface ShowLocationRepository extends JpaRepository<ShowLocation, Integer> {

    Optional<ShowLocation> findByFacilityId(String facilityId);
    Optional<ShowLocation> findByShowlocation(Integer showlocation);

    // ✅ 장소명으로 검색 (페이징 지원)
    Page<ShowLocation> findByLocationNameContaining(String keyword, Pageable pageable);
}
