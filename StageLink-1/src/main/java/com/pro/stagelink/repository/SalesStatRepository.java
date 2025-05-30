package com.pro.stagelink.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pro.stagelink.domain.SalesStat;

import java.util.List;
import java.util.Optional;


@Repository
public interface SalesStatRepository extends JpaRepository<SalesStat, Integer> {
	// 특정 년월의 매출 통계 조회
    Optional<SalesStat> findByYearAndMonth(int year, int month);
    
    // 매출 계산을 위한 네이티브 쿼리
    @Query(value = """
    	    SELECT COALESCE(SUM(
    	        CASE 
    	            WHEN ss.SEAT_CLASS = 'VIP' THEN s.SEAT_VIP_PRICE
    	            WHEN ss.SEAT_CLASS = 'R' THEN s.SEAT_R_PRICE
    	            WHEN ss.SEAT_CLASS = 'S' THEN s.SEAT_S_PRICE
    	            WHEN ss.SEAT_CLASS = 'A' THEN s.SEAT_A_PRICE
    	            ELSE 0
    	        END
    	    ), 0) as totalSales
    	    FROM TBL_RESERVATION r
    	    JOIN TBL_SHOWSEAT ss ON r.SEAT_ID = ss.SEAT_ID
    	    JOIN TBL_SHOW s ON r.SHOW_NO = s.SHOW_NO
    	    WHERE r.STATUS = 'CONFIRMED'
    	    AND YEAR(r.RESERVATION_DATE) = :year
    	    AND MONTH(r.RESERVATION_DATE) = :month
    	    """, nativeQuery = true)
    	Integer calculateMonthlySales(@Param("year") int year, @Param("month") int month);

    
    @Query(value = """
    	    SELECT 
    	        s.SHOW_NO,
    	        si.SHOW_NAME,
    	        ss.SEAT_CLASS,
    	        COUNT(*) AS reserved_count,
    	        CASE 
    	            WHEN ss.SEAT_CLASS = 'VIP' THEN s.SEAT_VIP_PRICE
    	            WHEN ss.SEAT_CLASS = 'R' THEN s.SEAT_R_PRICE
    	            WHEN ss.SEAT_CLASS = 'S' THEN s.SEAT_S_PRICE
    	            WHEN ss.SEAT_CLASS = 'A' THEN s.SEAT_A_PRICE
    	            ELSE 0
    	        END AS seat_price,
    	        COUNT(*) * 
    	        CASE 
    	            WHEN ss.SEAT_CLASS = 'VIP' THEN s.SEAT_VIP_PRICE
    	            WHEN ss.SEAT_CLASS = 'R' THEN s.SEAT_R_PRICE
    	            WHEN ss.SEAT_CLASS = 'S' THEN s.SEAT_S_PRICE
    	            WHEN ss.SEAT_CLASS = 'A' THEN s.SEAT_A_PRICE
    	            ELSE 0
    	        END AS sales
    	    FROM TBL_RESERVATION r
    	    JOIN TBL_SHOWSEAT ss ON r.SEAT_ID = ss.SEAT_ID
    	    JOIN TBL_SHOW s ON r.SHOW_NO = s.SHOW_NO
            JOIN TBL_SHOWINFO si ON s.SHOW_INFO = si.SHOW_INFO
    	    WHERE r.STATUS = 'CONFIRMED'
    	    AND YEAR(r.RESERVATION_DATE) = :year
    	    AND MONTH(r.RESERVATION_DATE) = :month
    	    GROUP BY s.SHOW_NO, ss.SEAT_CLASS
    	""", nativeQuery = true)
    	List<Object[]> getMonthlyShowSalesDetail(@Param("year") int year, @Param("month") int month);
}
