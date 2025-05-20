package com.pro.stagelink.service;

import com.pro.stagelink.domain.QnAnswer;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.QnAnswerDTO;
import com.pro.stagelink.repository.QnAnswerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnAnswerService {

    private final QnAnswerRepository qnAnswerRepository;
    private final ModelMapper modelMapper;

    // Q&A 전체 목록 (페이징 포함)
    public PageResponseDTO<QnAnswerDTO> getQnAnswers(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
            pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize(),
            Sort.by("questionNo").descending()
        );

        Page<QnAnswer> result = qnAnswerRepository.findAll(pageable);

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

    // Q&A 개수 반환
    public long getCount() {
        return qnAnswerRepository.count();
    }

    // 답변 수정
    @Transactional
    public void updateAnswer(int questionNo, QnAnswerDTO dto) {
        QnAnswer existing = qnAnswerRepository.findById(questionNo)
                .orElseThrow(() -> new IllegalArgumentException("Q&A 항목을 찾을 수 없습니다."));
        existing.setAnswerContents(dto.getAnswerContents());
        existing.setQnaRating(dto.getQnaRating());
        qnAnswerRepository.save(existing);
    }

    // 단건 조회 (선택사항)
    public Optional<QnAnswerDTO> getDetail(int questionNo) {
        return qnAnswerRepository.findById(questionNo)
                .map(qna -> modelMapper.map(qna, QnAnswerDTO.class));
    }
}
