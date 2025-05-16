package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Notice;
import com.pro.stagelink.dto.NoticeDTO;

public class NoticeMapper {
    public static NoticeDTO toDTO(Notice notice) {
        NoticeDTO dto = new NoticeDTO();
        dto.setNoticeNo(notice.getNotice_no());
        dto.setNoticeTitle(notice.getNoticeTitle());
        dto.setNoticeContents(notice.getNotice_contents());
        dto.setNoticeDate(notice.getNotice_date());
        return dto;
    }

    public static Notice toEntity(NoticeDTO dto) {
        Notice notice = new Notice();
        notice.setNotice_no(dto.getNoticeNo());
        notice.setNoticeTitle(dto.getNoticeTitle());
        notice.setNotice_contents(dto.getNoticeContents());
        notice.setNotice_date(dto.getNoticeDate());
        return notice;
    }
}