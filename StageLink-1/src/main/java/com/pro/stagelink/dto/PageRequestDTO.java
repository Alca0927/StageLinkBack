package com.pro.stagelink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    // ✅ 이름 검색용 필드 추가
    private String name;
    
    // ✅ type: 검색 타입 (예: t = title, l = location 등)
    private String type;

    // ✅ keyword: 검색어
    private String keyword;
    
 // Q&A 검색용 필드 (새로 추가)
    private String question;
}
