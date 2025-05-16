package com.pro.stagelink.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {
    private Long noticeNo;
    private String noticeContents;
    private LocalDateTime noticeDate;
	public Object getNoticeContents() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getNoticeContents1() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getNoticeDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object getNoticeNo() {
		return null;
	}
}
