package com.pro.stagelink.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // 복합키 비교를 위해 반드시 필요
@Embeddable // JPA에서 복합키로 사용할 수 있도록 지정
@Builder
public class ActorShowId implements Serializable {
	@Column(name = "ACTOR_NO")
	private int actorNo; // 배우 고유 번호 (TBL_ACTOR의 FK)


	@Column(name = "SHOW_INFO")
	private int showInfo; // 공연 정보 고유 번호 (TBL_SHOWINFO의 FK)
}
