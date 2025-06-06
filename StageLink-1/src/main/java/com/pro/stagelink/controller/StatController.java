package com.pro.stagelink.controller;

import com.pro.stagelink.dto.MemberStatDTO;
import com.pro.stagelink.dto.SalesShowStatDTO;
import com.pro.stagelink.dto.SalesStatDTO;
import com.pro.stagelink.service.MemberStatService;
import com.pro.stagelink.service.SalesStatService;
import com.pro.stagelink.service.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class StatController {
	private final MemberStatService memberStatService;
	private final SalesStatService salesStatService;
	
	@GetMapping("/stat/member/{year}/{month}")
	public MemberStatDTO getMemberStat(@PathVariable(name="year") int year, @PathVariable(name="month") int month) {
		return memberStatService.getMemberStat(year,month);
	}
	
	
	
    // 회원 통계 수동 실행용
    @PostMapping("/stat/member/{year}/{month}")
    public  MemberStatDTO generateMemberStat(@PathVariable(name="year") int year, @PathVariable(name="month") int month) {
        memberStatService.createMonthlyStat(year,month);
        
        return memberStatService.getMemberStat(year,month);
    }
    
    /**
     * 특정 년월의 매출 통계 조회
     */
    @GetMapping("/stat/sales/{year}/{month}")
    public ResponseEntity<SalesStatDTO> getSalesStat(
            @PathVariable("year") int year, 
            @PathVariable("month") int month) {
        
        log.info("매출 통계 조회 요청: {}년 {}월", year, month);
        
        try {
            SalesStatDTO salesStat = salesStatService.getSalesStat(year, month);
            return ResponseEntity.ok(salesStat);
        } catch (RuntimeException e) {
            log.warn("매출 통계 조회 실패: {}년 {}월, 오류: {}", year, month, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 특정 년월의 매출 통계 수동 재계산
     */
    @PostMapping("/sales/recalculate/{year}/{month}")
    public ResponseEntity<SalesStatDTO> recalculateSalesStat(
            @PathVariable("year") int year, 
            @PathVariable("month") int month) {
        
        log.info("매출 통계 재계산 요청: {}년 {}월", year, month);
        
        try {
            SalesStatDTO salesStat = salesStatService.recalculateSalesStat(year, month);
            log.info("매출 통계 재계산 요청: {}원",salesStat);
            return ResponseEntity.ok(salesStat);
        } catch (Exception e) {
            log.error("매출 통계 재계산 실패: {}년 {}월, 오류: {}", year, month, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 월별 통계 세부 사항
    // 월별 통계 세부 사항 - 경로 수정
    @GetMapping("/stat/show/{year}/{month}")
    public ResponseEntity<List<SalesShowStatDTO>> getMonthlyShowSalesDetail(
            @PathVariable("year") int year,
            @PathVariable("month") int month) {
        
        log.info("공연별 매출 상세 조회 요청: {}년 {}월", year, month);
        
        try {
            List<SalesShowStatDTO> result = salesStatService.getMonthlyShowSalesDetail(year, month);
            log.info("공연별 매출 상세 조회 결과: {} 건", result.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("공연별 매출 상세 조회 실패: {}년 {}월, 오류: {}", year, month, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
