package com.pro.stagelink.controller;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.service.NoticeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService service;

    public NoticeController(NoticeService service) {
        this.service = service;
    }

    @GetMapping
    public List<NoticeDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/count")
    public long getCount() {
        return service.getCount();
    }

    @PostMapping
    public void create(@RequestBody NoticeDTO dto) {
        service.save(dto);
    }
    
}