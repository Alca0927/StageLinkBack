package com.pro.stagelink.controller;

import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.service.QnAnswerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class QnAnswerController {

    private final QnAnswerService qnAnswerService;

    // Q&A 목록 조회 (페이징)
    @GetMapping("/qna/list")
    public PageResponseDTO<QnAnswerDTO> getQnAnswers(PageRequestDTO pageRequestDTO) {
        return qnAnswerService.getQnAnswers(pageRequestDTO);
    }

    // Q&A 개수 조회
    @GetMapping("/qna/count")
    public Map<String, Long> getCount() {
        return Map.of("count", qnAnswerService.getCount());
    }

    // Q&A 답변 수정
    @PutMapping("/qna/{questionNo}/answer")
    public Map<String, String> updateAnswer(@PathVariable("questionNo") int questionNo, @RequestBody QnAnswerDTO dto) {
        qnAnswerService.updateAnswer(questionNo, dto);
        return Map.of("RESULT", "SUCCESS");
    }

    // 단건 상세 조회 (선택)
    @GetMapping("/qna/{questionNo}")
    public QnAnswerDTO getDetail(@PathVariable("questionNo") int questionNo) {
        return qnAnswerService.getDetail(questionNo)
                .orElseThrow(() -> new IllegalArgumentException("Q&A 항목을 찾을 수 없습니다."));
    }
}