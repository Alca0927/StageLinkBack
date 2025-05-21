package com.pro.stagelink.service;

import com.pro.stagelink.domain.Notice;
import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    // 공지사항 목록 + 페이지 처리
    public PageResponseDTO<NoticeDTO> getNotices(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
            pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize(),
            Sort.by("noticeNo").descending()
        );

        Page<Notice> result = noticeRepository.findAll(pageable);

        List<NoticeDTO> dtoList = result.getContent().stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<NoticeDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    // 공지사항 등록
    public void save(NoticeDTO dto) {
        Notice entity = modelMapper.map(dto, Notice.class);
        noticeRepository.save(entity);
    }

    // 공지사항 전체 개수
    public long getCount() {
        return noticeRepository.count();
    }
    
 // 공지사항 상세 조회
    public NoticeDTO getNotice(Integer noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
        return modelMapper.map(notice, NoticeDTO.class);
    }


}
