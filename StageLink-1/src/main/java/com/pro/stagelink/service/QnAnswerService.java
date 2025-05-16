package com.pro.stagelink.service;

import com.pro.stagelink.domain.QnAnswer;
import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.mapper.QnAnswerMapper;
import com.pro.stagelink.repository.QnAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnAnswerService {

    private final QnAnswerRepository repository;
    private final QnAnswerMapper mapper; // mapper 주입

    public QnAnswerService(QnAnswerRepository repository, QnAnswerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<QnAnswerDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto) // 인스턴스를 통해 호출
                .collect(Collectors.toList());
    }

    public long getCount() {
        return repository.count(); // JPA 기본 제공 메서드 사용
    }

    public void updateAnswer(int questionNo, QnAnswerDTO dto) {
        QnAnswer existing = repository.findById(questionNo)
                .orElseThrow(() -> new IllegalArgumentException("Q&A 항목을 찾을 수 없습니다."));
        existing.setAnswerContents(dto.getAnswerContents());
        existing.setQnaRating(dto.getQnaRating());
        repository.save(existing);
    }
}