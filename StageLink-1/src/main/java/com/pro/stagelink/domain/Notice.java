package com.pro.stagelink.domain;

import java.time.LocalDateTime;

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
    private int notice_no;       // 공지번호
    private String notice_contents;     // 공지내용
    private String notice_date;   // 작성일
    private String noticeTitle; // 공지제목
}