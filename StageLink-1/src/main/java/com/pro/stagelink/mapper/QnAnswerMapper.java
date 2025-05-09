package com.pro.stagelink.mapper;

import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.domain.QnAnswer;

@Mapper(componentModel = "spring")
public interface QnAnswerMapper {
    QnAnswerDTO toDto(QnAnswer entity);
    QnAnswer toEntity(QnAnswerDTO dto);
}