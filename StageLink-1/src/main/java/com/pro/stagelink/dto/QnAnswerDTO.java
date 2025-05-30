package com.pro.stagelink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnAnswerDTO {
    private int questionNo;
    private int memberNo;
    private String questionContents;
    private String answerContents;
    private String createDate;
    
    @JsonIgnore
    private int qnaRating;
}