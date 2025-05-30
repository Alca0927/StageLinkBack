package com.pro.stagelink.repository;

import com.pro.stagelink.domain.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    // 🔍 이름으로 검색하는 메서드
    Page<Actor> findByActorNameContaining(String name, Pageable pageable);
}
