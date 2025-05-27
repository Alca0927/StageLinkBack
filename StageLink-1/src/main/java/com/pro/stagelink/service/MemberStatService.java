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

    public void createMonthlyStat() {
        // 오늘 날짜에서 전월 구하기
        LocalDate now = LocalDate.now();
        LocalDate targetDate = now.minusMonths(1);

        int year = targetDate.getYear();
        int month = targetDate.getMonthValue();

        int activeCount = memberRepository.countActiveMembers();
        int joinedCount = memberRepository.countJoinedMembersByYearAndMonth(year, month);

        MemberStat stat = MemberStat.builder()
                .memberSum(activeCount)
                .joinedMember(joinedCount)
                .year(year)
                .month(month)
                .createdDate(LocalDateTime.now())
                .build();

        memberStatRepository.save(stat);
    }
    
    
    public MemberStatDTO getMemberStat(int year, int month) {
    	Optional<MemberStat> result = memberStatRepository.findByYearAndMonth(year,month);
    	MemberStat memberStat = result.orElseThrow();
    	MemberStatDTO dto = modelMapper.map(memberStat, MemberStatDTO.class);
    	return dto;
    }
}
