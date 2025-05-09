package com.pro.stagelink.controller;

import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.service.QnAnswerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnAnswerController {

    private final QnAnswerService service;

    @GetMapping
    public List<QnAnswerDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public QnAnswerDTO getOne(@PathVariable Long id) {
        return service.getById(id);
    }
    
    @PutMapping("/{id}/answer")
    public void updateAnswer(@PathVariable Long id, @RequestBody Map<String, String> body) {
        service.updateAnswer(id, body.get("answerContents"));
    }

}