package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Notice;
import com.pro.stagelink.dto.NoticeDTO;

public class NoticeMapper {

    public static NoticeDTO toDTO(Notice notice) {
        NoticeDTO dto = new NoticeDTO();
        dto.setNoticeNo(notice.getNoticeNo());
        dto.setNoticeTitle(notice.getNoticeTitle());
        dto.setNoticeContent(notice.getNoticeContent());
        dto.setNoticeDate(notice.getNoticeDate());
        return dto;
    }

    public static Notice toEntity(NoticeDTO dto) {
        Notice notice = new Notice();
        notice.setNoticeNo(dto.getNoticeNo());
        notice.setNoticeTitle(dto.getNoticeTitle());
        notice.setNoticeContent(dto.getNoticeContent());
        notice.setNoticeDate(dto.getNoticeDate());
        return notice;
    }
}
