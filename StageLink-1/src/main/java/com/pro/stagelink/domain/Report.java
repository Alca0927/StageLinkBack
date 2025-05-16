package com.pro.stagelink.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_report")
@Getter
@Setter
public class Report {
    @Id
    private int reportNo;       // 신고 번호 (기본키)
    private int postNo;       // 게시글 번호
    private int suspectId;       // 피의자 회원번호 
    private int reporterId;     // 신고자 회원번호
    private String reportDate;   // 신고일자
    private String reportReason;     // 신고사유
}

