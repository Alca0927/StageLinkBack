package com.pro.stagelink.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "notice_no")
    private int noticeNo;  // 공지번호

    @Column(name = "notice_content")
    private String noticeContent;  // 공지내용

    @Column(name = "notice_date")
    private String noticeDate;  // 작성일

    @Column(name = "notice_title")
    private String noticeTitle;  // 공지제목
}
