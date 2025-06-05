package com.pro.stagelink.repository;

import com.pro.stagelink.domain.QnAnswer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnAnswerRepository extends JpaRepository<QnAnswer, Integer> {
    @Query("SELECT COUNT(q) FROM QnAnswer q")
    long countQnAnswers();
    
 // 기본 검색: 질문 내용에서 대소문자 구분 없이 검색
    Page<QnAnswer> findByQuestionContentsContainingIgnoreCase(String searchKeyword, Pageable pageable);
}