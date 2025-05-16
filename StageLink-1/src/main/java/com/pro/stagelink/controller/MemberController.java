package com.pro.stagelink.controller;

import com.pro.stagelink.dto.MemberDTO;
import com.pro.stagelink.dto.MemberStateUpdateDTO;
import com.pro.stagelink.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public Page<MemberDTO> getMembers(@RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return memberService.getMembers(name, page, size);
    }

    @GetMapping("/{memberNo}")
    public MemberDTO getMemberDetail(@PathVariable int memberNo) {
        return memberService.getMemberDetail(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }

    @PutMapping("/{memberNo}/state")
    public void updateMemberState(@PathVariable int memberNo, @RequestBody MemberStateUpdateDTO dto) {
        memberService.updateMemberState(memberNo, dto);
    }
}