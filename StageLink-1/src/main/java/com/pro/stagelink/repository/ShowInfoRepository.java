package com.pro.stagelink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.ShowInfo;

public interface ShowInfoRepository extends JpaRepository<ShowInfo, Integer> {

	Optional<ShowInfo> findById(int showInfo);

}
