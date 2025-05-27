package com.pro.stagelink.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberStatDTO {
	private Integer memberSum;
    private Integer joinedMember;
    private Integer year;
    private Integer month;
}
