package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.dto.MemberDTO;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDTO toDto(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMemberNo(member.getMemberNo());
        dto.setMemberId(member.getMemberId());
        dto.setName(member.getName());
        dto.setUserEmail(member.getUserEmail());
        dto.setMemberState(member.getMemberState());
        dto.setNickname(member.getNickname());
        dto.setAddress(member.getAddress());
        return dto;
    }
}