package com.pro.stagelink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Actor;
import com.pro.stagelink.domain.ActorShow;
import com.pro.stagelink.domain.ActorShowId;
import com.pro.stagelink.domain.ShowInfo;

public interface ActorShowRepository extends JpaRepository<ActorShow, ActorShowId> {
	// 복합키 기반으로 단건 조회
    Optional<ActorShow> findByActorAndShowInfo(Actor actor, ShowInfo showInfo);
    
    // 특정 배우가 출연한 공연 목록
    List<ActorShow> findByActor(Actor actor);
    
    // 특정 공연에 출연한 배우 목록
    List<ActorShow> findByShowInfo(ShowInfo showInfo);
}
