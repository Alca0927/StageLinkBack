package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qnanswer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionNo;

    private Long memberNo;

    private String questionContents;

    private String answerContents;

    private LocalDateTime createDate;
}
