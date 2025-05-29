package com.pro.stagelink.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {
    private Integer noticeNo;
    private String noticeTitle;
    private String noticeContent;
    private String noticeDate;
}