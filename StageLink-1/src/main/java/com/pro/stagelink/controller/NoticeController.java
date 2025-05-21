package com.pro.stagelink.controller;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.service.NoticeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 목록
    @GetMapping("/list")
    public PageResponseDTO<NoticeDTO> getNotices(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);

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
    
    @GetMapping("/{noticeNo}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable("noticeNo") Integer noticeNo) {
        NoticeDTO dto = noticeService.getNotice(noticeNo);
        return ResponseEntity.ok(dto);
    }

}
