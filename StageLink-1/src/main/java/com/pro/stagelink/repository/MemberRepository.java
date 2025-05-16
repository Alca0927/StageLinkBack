package com.pro.stagelink.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByMbrNameContainingIgnoreCase(String name, Pageable pageable);
}
