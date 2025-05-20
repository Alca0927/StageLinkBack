package com.pro.stagelink.dto;

import lombok.Data;

@Data
public class ReportDTO {
	private int reportNo;       // 신고 번호 (기본키)
    private int postNo;       // 게시글 번호
    private int suspectId;       // 피의자 회원번호 
    private int reporterId;     // 신고자 회원번호
    private String reportDate;   // 신고일자
    private String reportReason;     // 신고사유
}