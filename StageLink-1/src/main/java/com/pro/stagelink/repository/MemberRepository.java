package com.pro.stagelink.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.dto.MemberDTO;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Page<Member> findByNameContaining(String name, Pageable pageable);
}