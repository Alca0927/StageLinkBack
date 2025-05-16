package com.pro.stagelink.dto;

import java.time.LocalDateTime;

public class PostDTO {
    private String title;
    private String content;
    private String author; // 작성자 ID 또는 이름
    private LocalDateTime createdAt;
}
