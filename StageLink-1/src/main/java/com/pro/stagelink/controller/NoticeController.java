package com.pro.stagelink.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public void createNotice(@RequestBody NoticeDTO dto) {
        noticeService.createNotice(dto);
    }
    
    @GetMapping
    public List<NoticeDTO> getAll() {
        return noticeService.getAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody NoticeDTO dto) {
        noticeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noticeService.delete(id);
    }

}

