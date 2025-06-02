package com.pro.stagelink.controller;

import com.pro.stagelink.dto.MemberDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.service.MemberService;
import com.pro.stagelink.service.ReportService;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ReportService reportService;

    @GetMapping("/members")
    public PageResponseDTO<MemberDTO> getMembers(PageRequestDTO pageRequestDTO) {
        return memberService.getMembers(pageRequestDTO);
    }

    @GetMapping("/members/{memberNo}")
    public MemberDTO getMemberDetail(@PathVariable(name = "memberNo") Long memberNo) {
        return memberService.getMember(memberNo);
    }

    @PutMapping("/members/{memberNo}/state")
    public Map<String, String> updateMemberState(@PathVariable(name = "memberNo") Long memberNo, @RequestBody MemberDTO dto) {
        dto.setMemberNo(memberNo);
        memberService.updateMemberState(dto);
        return Map.of("RESULT", "SUCCESS");
    }
    
    @GetMapping("/members/count")
    public Map<String, Long> getMemberCount() {
        long count = memberService.getTotalCount();
        return Map.of("count", count);
    }
    
 // 요약 데이터 반환 (회원 수 + 신고 수)
    @GetMapping("/membermanager")
    public ResponseEntity<Map<String, Integer>> getMemberSummary() {
        int memberCount = (int) memberService.getTotalCount();
        int reportCount = (int) reportService.getCount();

        Map<String, Integer> summary = new HashMap<>();
        summary.put("memberCount", memberCount);
        summary.put("reportCount", reportCount);

        return ResponseEntity.ok(summary);
    }

}
