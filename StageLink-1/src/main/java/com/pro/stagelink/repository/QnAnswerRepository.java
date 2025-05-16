package com.pro.stagelink.repository;

import com.pro.stagelink.domain.QnAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnAnswerRepository extends JpaRepository<QnAnswer, Long> {
	
}
