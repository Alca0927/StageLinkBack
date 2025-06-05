package com.pro.stagelink.service;

import com.pro.stagelink.domain.QnAnswer;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.repository.QnAnswerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class QnAnswerService {

    private final QnAnswerRepository qnAnswerRepository;
    private final ModelMapper modelMapper;

 // ✅ Q&A 전체 목록 (페이징 + 검색 포함)
    public PageResponseDTO<QnAnswerDTO> getQnAnswers(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
            pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize(),
            Sort.by("questionNo").descending()
        );

        Page<QnAnswer> result;
        
        // 검색어가 있는 경우와 없는 경우를 구분하여 처리
        String searchKeyword = pageRequestDTO.getQuestion();
        log.info("찾는 검색어 : ", searchKeyword);
        
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            // 검색어가 있는 경우: 질문 내용에서 검색
            result = qnAnswerRepository.findByQuestionContentsContainingIgnoreCase(
                searchKeyword, pageable
            );
            
            log.info("🔍 Q&A 검색 실행 - 검색어: '{}', 결과 수: {}", 
                    searchKeyword, result.getTotalElements());
        } else {
            // 검색어가 없는 경우: 전체 목록 조회
            result = qnAnswerRepository.findAll(pageable);
            
            log.info("📋 Q&A 전체 목록 조회 - 총 개수: {}", result.getTotalElements());
        }

        List<QnAnswerDTO> dtoList = result.getContent().stream()
            .map(qna -> modelMapper.map(qna, QnAnswerDTO.class))
            .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<QnAnswerDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(totalCount)
            .build();
    }

    // ✅ Q&A 총 개수 반환
    public long getCount() {
        return qnAnswerRepository.count();
    }

    // ✅ Q&A 답변 수정 (answerContents만)
    @Transactional
    public void updateAnswer(int questionNo, QnAnswerDTO dto) {
        QnAnswer existing = qnAnswerRepository.findById(questionNo)
                .orElseThrow(() -> new IllegalArgumentException("Q&A 항목을 찾을 수 없습니다."));

        existing.setAnswerContents(dto.getAnswerContents()); // ✅ 답변만 수정
        qnAnswerRepository.save(existing); // ✅ 저장
    }

    // ✅ 단건 조회
    public Optional<QnAnswerDTO> getDetail(int questionNo) {
        return qnAnswerRepository.findById(questionNo)
                .map(qna -> modelMapper.map(qna, QnAnswerDTO.class));
    }
}
