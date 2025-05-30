package com.pro.stagelink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Actor;
import com.pro.stagelink.domain.ActorShow;
import com.pro.stagelink.domain.ActorShowId;
import com.pro.stagelink.domain.ShowInfo;

public interface ActorShowRepository extends JpaRepository<ActorShow, ActorShowId> {
    
	 Optional<ActorShow> findByActorAndShowInfo(Actor actor, ShowInfo showInfo);
	    List<ActorShow> findByActor(Actor actor);
	    List<ActorShow> findByShowInfo(ShowInfo showInfo);

	    // 🔍 검색용
	    Page<ActorShow> findByActor_ActorNameContaining(String keyword, Pageable pageable);
	    Page<ActorShow> findByShowInfo_ShowNameContaining(String keyword, Pageable pageable);
}
