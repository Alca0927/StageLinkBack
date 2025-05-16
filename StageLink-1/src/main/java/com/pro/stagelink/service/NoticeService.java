package com.pro.stagelink.service;

import com.pro.stagelink.domain.Notice;
import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.repository.NoticeRepository;
import com.pro.stagelink.mapper.NoticeMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    // 공지 등록
    public void createNotice(NoticeDTO dto) {
        Notice notice = noticeMapper.toEntity(dto);
        noticeRepository.save(notice);
    }

    // 전체 공지 조회
    public List<NoticeDTO> getAll() {
        return noticeRepository.findAll().stream()
                .map(noticeMapper::toDto)
                .collect(Collectors.toList());
    }

    // 공지 수정
    public void update(Long id, NoticeDTO dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
        notice.setNoticeContents(dto.getNoticeContents());
        notice.setNoticeDate(dto.getNoticeDate());
        noticeRepository.save(notice);
    }

    // 공지 삭제
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
