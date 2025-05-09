package com.pro.stagelink.mapper;

import com.pro.stagelink.domain.Member;
import com.pro.stagelink.dto.MemberDTO;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberDTO toDto(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMbrNo(member.getMbrNo());
        dto.setMbrId(member.getMbrId());
        dto.setMbrName(member.getMbrName());
        dto.setEmail(member.getEmail());
        dto.setStat(member.getStat());
        return dto;
    }
}
