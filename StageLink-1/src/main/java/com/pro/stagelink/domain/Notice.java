package com.pro.stagelink.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_notice")
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_no")
    private Integer noticeNo;

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @Column(name = "notice_date")
    private String noticeDate;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;
    
    public void changeNoticeContent(String noticeContent) {
    	this.noticeContent = noticeContent;
    }
    
    public void changeNoticeTitle(String noticeTitle) {
    	this.noticeTitle = noticeTitle;
    }
}
