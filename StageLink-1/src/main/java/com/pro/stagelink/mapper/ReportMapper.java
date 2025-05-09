package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Report;
import com.pro.stagelink.dto.ReportDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper {
 //   @Mapping(source = "reporterMember.mbrId", target = "reporterId")
    ReportDTO toDto(Report report);
}
