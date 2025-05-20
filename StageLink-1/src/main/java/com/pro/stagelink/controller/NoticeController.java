package com.pro.stagelink.controller;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.service.NoticeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 목록
    @GetMapping("/list")
    public PageResponseDTO<NoticeDTO> getNotices(PageRequestDTO pageRequestDTO) {
        return noticeService.getNotices(pageRequestDTO);
    }

    // 공지사항 개수
    @GetMapping("/count")
    public Map<String, Long> getCount() {
        return Map.of("count", noticeService.getCount());
    }

    // 공지사항 등록
    @PostMapping
    public void create(@RequestBody NoticeDTO dto) {
        noticeService.save(dto);
    }
}
