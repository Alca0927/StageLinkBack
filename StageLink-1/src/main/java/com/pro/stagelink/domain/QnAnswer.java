package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_qnanswer")
@Getter
@Setter
public class QnAnswer {
    @Id
    private int questionNo;       // Q&A번호
    private int memberNo;     // 회원번호   
    private String questionContents; // 질문
    private String answerContents; // 답변
    private String createDate; // 작성일
    private int qnaRating;   // 평점
}