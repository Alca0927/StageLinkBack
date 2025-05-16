package com.pro.stagelink.service;

import com.pro.stagelink.repository.MemberRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.dto.MemberDTO;
import com.pro.stagelink.mapper.MemberMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public Page<MemberDTO> getMemberList(String search, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Member> members = memberRepository.findByMbrNameContainingIgnoreCase(search, pageable);
        return members.map(memberMapper::toDto);
    }
    
    public MemberDTO getMemberById(Long id) {
        return memberRepository.findById(id)
            .map(memberMapper::toDto)
            .orElseThrow(() -> new RuntimeException("회원 없음"));
    }
    
    @Transactional
    public void updateMemberStatus(Long id, String stat) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        member.setStat(stat); // 예: "ACTIVE", "BLOCKED"
    }
}