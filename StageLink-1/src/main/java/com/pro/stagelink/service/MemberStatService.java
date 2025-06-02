package com.pro.stagelink.service;

import com.pro.stagelink.domain.MemberStat;
import com.pro.stagelink.dto.MemberStatDTO;
import com.pro.stagelink.repository.MemberRepository;
import com.pro.stagelink.repository.MemberStatRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberStatService {
	private final ModelMapper modelMapper;
	private final MemberRepository memberRepository;
    private final MemberStatRepository memberStatRepository;

    public MemberStatDTO createMonthlyStat(int year, int month) {
        int activeCount = memberRepository.countActiveMembers();
        int joinedCount = memberRepository.countJoinedMembersByYearAndMonth(year, month);

        
        // 기존 데이터 존재 여부 확인
        Optional<MemberStat> existingStat = memberStatRepository.findByYearAndMonth(year, month);
        
        if (existingStat.isPresent()) {
        	MemberStat memberStat = existingStat.get();
        	// 변경
        	memberStat.updateJoinedMember(joinedCount);
        	memberStat.updateMemberSum(activeCount);
        	// 저장
        	memberStatRepository.save(memberStat);
        } else {
        	// 데이터 생성
            MemberStat memberStat = MemberStat.builder()
                    .memberSum(activeCount)
                    .joinedMember(joinedCount)
                    .year(year)
                    .month(month)
                    .createdDate(LocalDateTime.now())
                    .build();
            // 저장
        	memberStatRepository.save(memberStat);
        }
        return getMemberStat(year, month);
    }
    
    
    public MemberStatDTO getMemberStat(int year, int month) {
    	Optional<MemberStat> result = memberStatRepository.findByYearAndMonth(year,month);
    	MemberStat memberStat = result.orElseThrow();
    	MemberStatDTO dto = modelMapper.map(memberStat, MemberStatDTO.class);
    	return dto;
    }
}
