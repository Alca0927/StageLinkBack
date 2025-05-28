package com.pro.stagelink.controller;

import com.pro.stagelink.service.NoticeService;
import com.pro.stagelink.service.QnAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/noticemanager")
public class NoticeMainController {

    private final NoticeService noticeService;
    private final QnAnswerService qnaService;

    public NoticeMainController(NoticeService noticeService, QnAnswerService qnaService) {
        this.noticeService = noticeService;
        this.qnaService = qnaService;
    }

    // 요약 데이터 반환 (공지 수 + Q&A 수)
    @GetMapping
    public ResponseEntity<Map<String, Integer>> getNoticeSummary() {
        int noticeCount = (int) noticeService.getCount();
        int qnaCount = (int) qnaService.getCount();

        Map<String, Integer> summary = new HashMap<>();
        summary.put("noticeCount", noticeCount);
        summary.put("qnaCount", qnaCount);

        return ResponseEntity.ok(summary);
    }
}
