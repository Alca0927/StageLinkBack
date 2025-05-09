package com.pro.stagelink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.service.KopisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KopisController {
	private final KopisService kopisService;

    @GetMapping("/fetch-kopis")
    public ResponseEntity<String> fetchKopisData() {
        kopisService.fetchAndSaveData();
        return ResponseEntity.ok("KOPIS 데이터 수집 완료");
    }
}
