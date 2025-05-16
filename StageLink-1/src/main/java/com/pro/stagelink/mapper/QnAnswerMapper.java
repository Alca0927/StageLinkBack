package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.QnAnswer;
import com.pro.stagelink.dto.QnAnswerDTO;

import org.springframework.stereotype.Component;

@Component
public class QnAnswerMapper {

    public QnAnswerDTO toDto(QnAnswer qnAnswer) {
    	QnAnswerDTO dto = new QnAnswerDTO();
        dto.setQuestionNo(qnAnswer.getQuestionNo());
        dto.setMemberNo(qnAnswer.getMemberNo());
        dto.setQuestionContents(qnAnswer.getQuestionContents());
        dto.setAnswerContents(qnAnswer.getAnswerContents());
        dto.setCreateDate(qnAnswer.getCreateDate());
        dto.setQnaRating(qnAnswer.getQnaRating());
        return dto;
    }
}
