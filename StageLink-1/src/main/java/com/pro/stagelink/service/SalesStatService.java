package com.pro.stagelink.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.stagelink.domain.SalesStat;
import com.pro.stagelink.dto.SalesShowStatDTO;
import com.pro.stagelink.dto.SalesStatDTO;
import com.pro.stagelink.repository.SalesStatRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class SalesStatService {
private final SalesStatRepository salesStatRepository;
    
	/**
	 * 특정 년월의 매출 통계 조회
	 */
	public SalesStatDTO getSalesStat(int year, int month) {
	    log.info("매출 통계 조회 요청: {}년 {}월", year, month);
	    
	    Optional<SalesStat> salesStat = salesStatRepository.findByYearAndMonth(year, month);
	    
	    if (salesStat.isEmpty()) {
	        log.warn("해당 기간의 매출 통계가 존재하지 않습니다: {}년 {}월", year, month);
	        throw new RuntimeException("해당 기간의 매출 통계가 존재하지 않습니다.");
	    }
	    
	    SalesStatDTO result = entityToDto(salesStat.get());
	    log.info("매출 통계 조회 결과: {}", result);
	    return result;
	}
    
	/**
	 * 매출 통계 계산 및 저장
	 */
	public void calculateAndSaveSalesStat(int year, int month) {
	    log.info("매출 통계 계산 시작: {}년 {}월", year, month);
	    
	    // 해당 월의 총 매출 계산
	    Integer totalSales = salesStatRepository.calculateMonthlySales(year, month);
	    
	    // null 체크 및 기본값 설정
	    if (totalSales == null) {
	        totalSales = 0;
	        log.warn("매출 계산 결과가 null입니다. 0으로 설정: {}년 {}월", year, month);
	    }
	    
	    log.info("계산된 매출: {}년 {}월 = {}", year, month, totalSales);
	    
	    // 기존 데이터 존재 여부 확인
	    Optional<SalesStat> existingStat = salesStatRepository.findByYearAndMonth(year, month);
	    
	    if (existingStat.isPresent()) {
	        // 기존 데이터 업데이트
	        SalesStat salesStat = existingStat.get();
	        salesStat.updateSales(totalSales);
	        salesStatRepository.save(salesStat);
	        log.info("매출 통계 업데이트 완료: {}년 {}월, 매출: {}", year, month, totalSales);
	    } else {
	        // 새 데이터 삽입
	        SalesStat newSalesStat = SalesStat.builder()
	                .year(year)
	                .month(month)
	                .salesSum(totalSales)
	                .build();
	        salesStatRepository.save(newSalesStat);
	        log.info("매출 통계 신규 생성 완료: {}년 {}월, 매출: {}", year, month, totalSales);
	    }
	}
    
    /**
     * 매월 1일 00시에 전월 매출 통계 자동 계산
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyStatCalculation() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int year = lastMonth.getYear();
        int month = lastMonth.getMonthValue();
        
        log.info("스케줄링된 매출 통계 계산 시작: {}년 {}월", year, month);
        
        try {
            calculateAndSaveSalesStat(year, month);
            log.info("스케줄링된 매출 통계 계산 완료: {}년 {}월", year, month);
        } catch (Exception e) {
            log.error("스케줄링된 매출 통계 계산 실패: {}년 {}월, 오류: {}", year, month, e.getMessage());
        }
    }
    
    /**
     * 수동으로 특정 월의 매출 통계 재계산
     */
    public SalesStatDTO recalculateSalesStat(int year, int month) {
        calculateAndSaveSalesStat(year, month);
        return getSalesStat(year, month);
    }
    
    /**
     * Entity to DTO 변환
     */
    private SalesStatDTO entityToDto(SalesStat salesStat) {
        return SalesStatDTO.builder()
                .id(salesStat.getId())
                .salesSum(salesStat.getSalesSum())
                .year(salesStat.getYear())
                .month(salesStat.getMonth())
                .createdDate(salesStat.getCreatedDate())
                .build();
    }
    
    public List<SalesShowStatDTO> getMonthlyShowSalesDetail(int year, int month) {
        List<Object[]> results = salesStatRepository.getMonthlyShowSalesDetail(year, month);
        return results.stream()
                .map(r -> new SalesShowStatDTO(
                        ((Number) r[0]).intValue(),
                        (String) r[1],
                        (String) r[2],
                        ((Number) r[3]).intValue(),
                        ((Number) r[4]).intValue(),
                        ((Number) r[5]).intValue()
                )).toList();
    }
}
