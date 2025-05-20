package com.pro.stagelink.controller;

import com.pro.stagelink.dto.MemberDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.service.MemberService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public PageResponseDTO<MemberDTO> getMembers(PageRequestDTO pageRequestDTO) {
        return memberService.getMembers(pageRequestDTO);
    }

    @GetMapping("/{memberNo}")
    public MemberDTO getMemberDetail(@PathVariable(name = "memberNo") int memberNo) {
        return memberService.getMember(memberNo);
    }

    @PutMapping("/{memberNo}/state")
    public Map<String, String> updateMemberState(@PathVariable(name = "memberNo") int memberNo, @RequestBody MemberDTO dto) {
        dto.setMemberNo(memberNo);
        memberService.updateMemberState(dto);
        return Map.of("RESULT", "SUCCESS");
    }
}
