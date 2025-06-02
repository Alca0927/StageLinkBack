package com.pro.stagelink.controller;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.service.NoticeService;
import com.pro.stagelink.service.QnAnswerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final QnAnswerService qnaService;

    // 🔹 공지사항 목록
    @GetMapping("/notices/list")
    public PageResponseDTO<NoticeDTO> getNotices(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);

        return noticeService.getNotices(pageRequestDTO);
    }

    // 🔹 공지사항 개수
    @GetMapping("/notices/count")
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
    @GetMapping("/notices/{noticeNo}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable("noticeNo") Integer noticeNo) {
        NoticeDTO dto = noticeService.getNotice(noticeNo);
        return ResponseEntity.ok(dto);
    }
    
    // 공지사항 수정
    @PutMapping("/notices/{noticeNo}")
    public Map<String, String> modify(@PathVariable(name = "noticeNo") int noticeNo, @RequestBody NoticeDTO dto){
    	dto.setNoticeNo(noticeNo);
    	log.info("Modify : " + dto);
    	noticeService.modify(dto);
    	
    	return Map.of("RESULT","SUCCESS");
    }
    
 // 요약 데이터 반환 (공지 수 + Q&A 수)
    @GetMapping("/noticemanager")
    public ResponseEntity<Map<String, Integer>> getNoticeSummary() {
        int noticeCount = (int) noticeService.getCount();
        int qnaCount = (int) qnaService.getCount();

        Map<String, Integer> summary = new HashMap<>();
        summary.put("noticeCount", noticeCount);
        summary.put("qnaCount", qnaCount);

        return ResponseEntity.ok(summary);
    }
}
