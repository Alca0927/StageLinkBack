package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Notice;
import com.pro.stagelink.dto.NoticeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    NoticeDTO toDto(Notice notice);
    Notice toEntity(NoticeDTO dto);
}
