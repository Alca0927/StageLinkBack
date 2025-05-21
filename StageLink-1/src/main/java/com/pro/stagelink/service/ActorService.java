package com.pro.stagelink.service;

import com.pro.stagelink.dto.ActorDTO;
import com.pro.stagelink.dto.ActorShowDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;

public interface ActorService {
	// 등록
	int register(ActorDTO actorDTO);
	int register(ActorShowDTO actorShowDTO);
		
	// 조회
	ActorDTO getActor(int actorNo);
	ActorShowDTO getActorShow(int actorNo, int showInfoId);
		
	// 수정
	void modify(ActorDTO actorDTO);
	void modify(ActorShowDTO actorShowDTO);
		
	// 삭제
	//void removeActor(Integer actorNo);
	//void removeActorShow(int actorNo, int showInfoId);
	
	// 목록 (페이징 구현) ---------------------------
	PageResponseDTO<ActorDTO> ActorList(PageRequestDTO pageRequestDTO);
	PageResponseDTO<ActorShowDTO> ActorShowList(PageRequestDTO pageRequestDTO);
}
