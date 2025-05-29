package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_SALES_ST")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SalesStat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "SALES_SUM", nullable = false)
    private int salesSum;
    
    @Column(name = "YEAR", nullable = false)
    private int year;
    
    @Column(name = "MONTH", nullable = false)
    private int month;
    
    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;
    
    // 매출 업데이트 메소드
    public void updateSales(int totalSales) {
        this.salesSum = totalSales;
    }
}
