package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Notice;
import com.pro.stagelink.dto.NoticeDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class NoticeMapperTest {

    private final NoticeMapper mapper = Mappers.getMapper(NoticeMapper.class);

    @Test
    public void testToDto() {
        Notice notice = new Notice();
        notice.setNoticeContents("공지사항입니다.");
        notice.setNoticeDate("2024-05-10");

        NoticeDTO dto = mapper.toDto(notice);

        assertThat(dto.getNoticeNo()).isEqualTo(1L);
        assertThat(dto.getNoticeContents()).isEqualTo("공지사항입니다.");
        assertThat(dto.getNoticeDate()).isEqualTo("2024-05-10");
    }

    
}
