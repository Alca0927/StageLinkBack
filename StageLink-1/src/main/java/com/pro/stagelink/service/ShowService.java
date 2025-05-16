package com.pro.stagelink.service;

import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.dto.ShowLocationDTO;

public interface ShowService {
	// 등록
	Integer register(ShowDTO showDTO);
	int register(ShowInfoDTO showInfoDTO);
	int register(ShowLocationDTO showLocationDTO);
	
	// 조회
	ShowDTO getShow(int showNo);
	ShowInfoDTO getShowInfo(int showInfo);
	ShowLocationDTO getShowlocation(int showlocation);
	
	// 수정
	void modify(ShowDTO showDTO);
	void modify(ShowInfoDTO showInfoDTO);
	void modify(ShowLocationDTO showLocationDTO);
	
	// 삭제
	void removeShow(Integer tno);
	void removeShowInfo(Integer tno);
	void removeShowLocation(Integer tno);
	
	// 목록 (페이징 구현) ---------------------------
	PageResponseDTO<ShowDTO> showList(PageRequestDTO pageRequestDTO);
	PageResponseDTO<ShowInfoDTO> showInfoList(PageRequestDTO pageRequestDTO);
	PageResponseDTO<ShowLocationDTO> showLocationList(PageRequestDTO pageRequestDTO);
}
