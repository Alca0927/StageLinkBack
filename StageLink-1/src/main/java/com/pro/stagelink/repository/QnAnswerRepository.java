package com.pro.stagelink.repository;

import com.pro.stagelink.domain.QnAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnAnswerRepository extends JpaRepository<QnAnswer, Integer> {
    @Query("SELECT COUNT(q) FROM QnAnswer q")
    long countQnAnswers();
}