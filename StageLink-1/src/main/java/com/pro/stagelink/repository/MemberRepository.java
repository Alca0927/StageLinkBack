package com.pro.stagelink.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro.stagelink.entity.Member;
import com.pro.stagelink.dto.MemberDTO;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // ✅ 이름 기준 부분 검색 + 페이징
    Page<Member> findByNameContaining(String name, Pageable pageable);
    
    // 통계를 위한 쿼리문
    @Query("SELECT COUNT(m) FROM Member m WHERE m.memberState = 'ACTIVE'")
    int countActiveMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE YEAR(m.joinedDate) = :year AND MONTH(m.joinedDate) = :month")
    int countJoinedMembersByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
