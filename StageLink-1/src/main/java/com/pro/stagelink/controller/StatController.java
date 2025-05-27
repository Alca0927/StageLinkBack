package com.pro.stagelink.controller;

import com.pro.stagelink.dto.MemberStatDTO;
import com.pro.stagelink.service.MemberStatService;
import com.pro.stagelink.service.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class StatController {
	private final MemberStatService memberStatService;
	
	@GetMapping("/stat/member/{year}/{month}")
	public MemberStatDTO getMemberStat(@PathVariable(name="year") int year, @PathVariable(name="month") int month) {
		return memberStatService.getMemberStat(year,month);
	}
	
	
	
    // 회원 통계 수동 실행용
    @PostMapping("/generate")
    public String generateMonthlyStat() {
        memberStatService.createMonthlyStat();
        return "통계 생성 완료";
    }
}
