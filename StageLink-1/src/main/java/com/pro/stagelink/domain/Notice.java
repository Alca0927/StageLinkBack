package com.pro.stagelink.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeNo;

    @Column(nullable = false)
    private String noticeContents;

    private LocalDateTime noticeDate;

	public void setNoticeContents(Object noticeContents2) {
		// TODO Auto-generated method stub
		
	}

	public void setNoticeDate(Object noticeDate2) {
		// TODO Auto-generated method stub
		
	}
}
