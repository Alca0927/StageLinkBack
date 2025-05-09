package com.pro.stagelink.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnAnswerDTO {
    private Long questionNo;
    private Long memberNo;
    private String questionContents;
    private String answerContents;
    private LocalDateTime createDate;
}