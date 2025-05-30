package com.pro.stagelink.service;

import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.dto.ShowLocationDTO;

public interface ShowService {
    Integer register(ShowDTO showDTO);
    int register(ShowInfoDTO showInfoDTO);
    int register(ShowLocationDTO showLocationDTO);

    ShowDTO getShow(int showNo);
    ShowInfoDTO getShowInfo(int showInfo);
    ShowLocationDTO getShowlocation(int showlocation);

    void modify(ShowDTO showDTO);
    void modify(ShowInfoDTO showInfoDTO);
    void modify(ShowLocationDTO showLocationDTO);

    void removeShow(Integer tno);
    void removeShowInfo(Integer tno);
    void removeShowLocation(Integer tno);

    PageResponseDTO<ShowDTO> showList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<ShowInfoDTO> showInfoList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<ShowLocationDTO> showLocationList(PageRequestDTO pageRequestDTO);
    
    int getTotalCount();  // 전체 공연 수

}
