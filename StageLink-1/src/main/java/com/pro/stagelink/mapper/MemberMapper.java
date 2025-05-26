package com.pro.stagelink.mapper;

import org.springframework.stereotype.Component;

import com.pro.stagelink.entity.Member;
import com.pro.stagelink.dto.MemberDTO;

@Component
public class MemberMapper {

    public MemberDTO toDto(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMemberNo(member.getMemberNo());
        dto.setUserId(member.getUserId());
        dto.setName(member.getName());
        dto.setGender(member.getGender());
        dto.setUserEmail(member.getUserEmail());
        dto.setMemberState(member.getMemberState());
        dto.setNickname(member.getNickname());
        return dto;
    }
}