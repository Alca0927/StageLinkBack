package com.pro.stagelink.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_member_st")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "member_sum")
    private Integer memberSum;

    @Column(name = "joined_member")
    private Integer joinedMember;

    private Integer year;
    private Integer month;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    // 회원 업데이트
    public void updateJoinedMember(int joinedCount) {
    	this.joinedMember = joinedCount;
    }
    
    public void updateMemberSum(int activeCount) {
    	this.memberSum = activeCount;
    }
}
