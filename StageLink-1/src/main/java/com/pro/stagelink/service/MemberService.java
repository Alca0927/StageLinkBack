package com.pro.stagelink.service;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.dto.MemberDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.mapper.MemberMapper;
import com.pro.stagelink.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    // 단일 회원 조회
    public MemberDTO getMember(int memberNo){
        Optional<Member> result = memberRepository.findById(memberNo);
        Member member = result.orElseThrow();
        return modelMapper.map(member, MemberDTO.class);
    }

    // 회원 상태 변경
    public void updateMemberState(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findById(memberDTO.getMemberNo());
        Member member = result.orElseThrow();
        member.setMemberState(memberDTO.getMemberState());
        memberRepository.save(member);
    }

    // 회원 목록 조회 (이름 검색 포함)
    public PageResponseDTO<MemberDTO> getMembers(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("memberNo").descending()
        );

        Page<Member> result;

        // ✅ 이름 검색 조건 처리
        if (pageRequestDTO.getName() != null && !pageRequestDTO.getName().isBlank()) {
            result = memberRepository.findByNameContaining(pageRequestDTO.getName(), pageable);
        } else {
            result = memberRepository.findAll(pageable);
        }

        List<MemberDTO> dtoList = result.getContent().stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<MemberDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }
}
