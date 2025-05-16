package com.pro.stagelink.service;

import com.pro.stagelink.repository.QnAnswerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.pro.stagelink.domain.QnAnswer;
import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.mapper.QnAnswerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QnAReplyService {

    private final QnAnswerRepository qnAnswerRepository;
    private final QnAnswerMapper qnAnswerMapper;
    
    public Page<QnAnswerDTO> getqnAnswerList(String search, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<QnAnswer> qnAnswer = qnAnswerRepository.findByQnANoContainingIgnoreCase(search, pageable);
        return qnAnswer.map(qnAnswerMapper::toDto);
    }
    
    public QnAnswerDTO getQnAnswerByNo(int no) {
        return qnAnswerRepository.findById(no)
            .map(qnAnswerMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Q&A 없음"));
    }
  
    @Transactional
    public void modify(int no, String answer) {
    	QnAnswer qnAnswer = qnAnswerRepository.findById(no)
            .orElseThrow(() -> new RuntimeException("Q&A가 존재하지 않습니다."));
    	qnAnswer.setAnswer_contents(answer); 
    } 
}