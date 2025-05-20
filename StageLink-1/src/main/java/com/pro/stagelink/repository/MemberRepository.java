package com.pro.stagelink.repository;

import com.pro.stagelink.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    // ✅ 이름 기준 부분 검색 + 페이징
    Page<Member> findByNameContaining(String name, Pageable pageable);
}
