package com.pro.stagelink.service;

import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.domain.QnAnswer;
import com.pro.stagelink.mapper.QnAnswerMapper;
import com.pro.stagelink.repository.QnAnswerRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnAnswerService {

    private final QnAnswerRepository repository;
    private final QnAnswerMapper mapper;

    public List<QnAnswerDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public QnAnswerDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));
    }

    public void updateAnswer(Long id, String answerContents) {
        QnAnswer q = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));
        q.setAnswerContents(answerContents);
        repository.save(q);
    }
}