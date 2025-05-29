package com.pro.stagelink.controller;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    // 🔹 공지사항 목록
    @GetMapping("/list")
    public PageResponseDTO<NoticeDTO> getNotices(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);

        return noticeService.getNotices(pageRequestDTO);
    }

    // 🔹 공지사항 개수
    @GetMapping("/count")
    public Map<String, Long> getCount() {
        return Map.of("count", noticeService.getCount());
    }

    // ✅ 공지사항 등록 (수정됨)
    @PostMapping
    public ResponseEntity<String> create(@RequestBody NoticeDTO dto) {
        try {
            log.info("📥 공지사항 등록 요청: {}", dto);
            noticeService.save(dto); // DB 저장 로직 호출
            return ResponseEntity.ok("공지사항이 등록되었습니다.");
        } catch (Exception e) {
            log.error("❌ 공지사항 등록 실패", e);
            return ResponseEntity.status(500).body("공지사항 등록 중 오류가 발생했습니다.");
        }
    }

    // 🔹 공지사항 상세 조회
    @GetMapping("/{noticeNo}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable("noticeNo") Integer noticeNo) {
        NoticeDTO dto = noticeService.getNotice(noticeNo);
        return ResponseEntity.ok(dto);
    }
}
