package com.pro.stagelink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.MemberStat;

public interface MemberStatRepository extends JpaRepository<MemberStat, Integer> {

	Optional<MemberStat> findByYearAndMonth(int year, int month);

}
