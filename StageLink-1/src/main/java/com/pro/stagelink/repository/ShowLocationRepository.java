package com.pro.stagelink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.ShowLocation;


public interface ShowLocationRepository extends JpaRepository<ShowLocation, Integer> {
	Optional<ShowLocation> findByFacilityId(String facilityId);
	Optional<ShowLocation> findByShowlocation(Integer showlocation);	
}
