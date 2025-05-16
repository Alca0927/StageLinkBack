package com.pro.stagelink.controller;

import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.service.QnAnswerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
public class QnAnswerController {

    private final QnAnswerService service;

    public QnAnswerController(QnAnswerService service) {
        this.service = service;
    }

    @GetMapping
    public List<QnAnswerDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/count")
    public long getCount() {
        return service.getCount();
    }

    @PutMapping("/{questionNo}/answer")
    public void updateAnswer(@PathVariable int questionNo, @RequestBody QnAnswerDTO dto) {
        service.updateAnswer(questionNo, dto);
    }
}
