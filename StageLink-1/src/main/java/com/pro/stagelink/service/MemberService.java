package com.pro.stagelink.service;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.dto.MemberDTO;
import com.pro.stagelink.dto.MemberStateUpdateDTO;
import com.pro.stagelink.mapper.MemberMapper;
import com.pro.stagelink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    
    public Page<MemberDTO> getMembers(String name, int page, int size) {
        return memberRepository.findByNameContaining(name, PageRequest.of(page, size))
                .map(memberMapper::toDto);
    }

    public Optional<MemberDTO> getMemberDetail(int memberNo) {
        return memberRepository.findById(memberNo).map(memberMapper::toDto);
    }

    public void updateMemberState(int memberNo, MemberStateUpdateDTO dto) {
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.setMemberState(dto.getMemberState());
        memberRepository.save(member);
    }

}