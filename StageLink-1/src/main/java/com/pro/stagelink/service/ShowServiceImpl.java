package com.pro.stagelink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pro.stagelink.domain.Show;
import com.pro.stagelink.domain.ShowInfo;
import com.pro.stagelink.domain.ShowLocation;
import com.pro.stagelink.domain.ShowSeat;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.dto.ShowLocationDTO;
import com.pro.stagelink.repository.ShowInfoRepository;
import com.pro.stagelink.repository.ShowLocationRepository;
import com.pro.stagelink.repository.ShowRepository;
import com.pro.stagelink.repository.ShowSeatRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ModelMapper modelMapper;
    private final ShowRepository showRepository;
    private final ShowInfoRepository showInfoRepository;
    private final ShowLocationRepository showLocationRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    public Integer register(ShowDTO showDTO) {
        Show show = modelMapper.map(showDTO, Show.class);
        Show savedShow = showRepository.save(show);
        createSeats(savedShow, "R", showDTO.getSeatRCount());
        createSeats(savedShow, "S", showDTO.getSeatSCount());
        createSeats(savedShow, "A", showDTO.getSeatACount());
        createSeats(savedShow, "VIP", showDTO.getSeatVipCount());
        return savedShow.getShowNo();
    }

    @Override
    public int register(ShowInfoDTO showInfoDTO) {
        ShowInfo showInfo = modelMapper.map(showInfoDTO, ShowInfo.class);
        ShowInfo savedShowInfo = showInfoRepository.save(showInfo);
        return savedShowInfo.getShowInfo();
    }

    @Override
    public int register(ShowLocationDTO showLocationDTO) {
        ShowLocation showLocation = modelMapper.map(showLocationDTO, ShowLocation.class);
        ShowLocation savedShowLocation = showLocationRepository.save(showLocation);
        return savedShowLocation.getShowlocation();
    }

    private void createSeats(Show show, String seatClass, int count) {
        List<ShowSeat> seats = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ShowSeat seat = new ShowSeat();
            seat.setSeatClass(seatClass);
            seat.setSeatNumber(i);
            seat.setSeatReserved(0);
            seat.setShow(show);
            seats.add(seat);
        }
        showSeatRepository.saveAll(seats);
    }

    @Override
    public ShowDTO getShow(int showNo) {
        Optional<Show> result = showRepository.findById(showNo);
        Show show = result.orElseThrow();
        return modelMapper.map(show, ShowDTO.class);
    }

    @Override
    public ShowInfoDTO getShowInfo(int showInfo) {
        Optional<ShowInfo> result = showInfoRepository.findById(showInfo);
        ShowInfo showinfo = result.orElseThrow();
        ShowInfoDTO dto = modelMapper.map(showinfo, ShowInfoDTO.class);

        if (showinfo.getShowLocation() != null) {
            ShowLocationDTO showLocationDTO = modelMapper.map(showinfo.getShowLocation(), ShowLocationDTO.class);
            dto.setShowLocationDTO(showLocationDTO);
        }

        return dto;
    }

    @Override
    public ShowLocationDTO getShowlocation(int showlocation) {
        Optional<ShowLocation> result = showLocationRepository.findById(showlocation);
        ShowLocation showLocation = result.orElseThrow();
        return modelMapper.map(showLocation, ShowLocationDTO.class);
    }

    @Override
    public void modify(ShowDTO showDTO) {
        Optional<Show> result = showRepository.findById(showDTO.getShowNo());
        Show show = result.orElseThrow();
        ShowInfo showInfo = modelMapper.map(showDTO.getShowInfoDTO(), ShowInfo.class);
        show.changeShowInfo(showInfo);
        show.changeShowStartTime(showDTO.getShowStartTime());
        show.changeShowEndTime(showDTO.getShowEndTime());
        show.changeSeatRPrice(showDTO.getSeatRPrice());
        show.changeSeatAPrice(showDTO.getSeatAPrice());
        show.changeSeatSPrice(showDTO.getSeatSPrice());
        show.changeSeatVipPrice(showDTO.getSeatVipPrice());
        show.changeSeatRCount(showDTO.getSeatRCount());
        show.changeSeatACount(showDTO.getSeatACount());
        show.changeSeatSCount(showDTO.getSeatSCount());
        show.changeSeatVipCount(showDTO.getSeatVipCount());
        show.changeShowState(showDTO.getShowState());
        showRepository.save(show);
    }

    @Override
    public void modify(ShowInfoDTO showInfoDTO) {
        Optional<ShowInfo> result = showInfoRepository.findById(showInfoDTO.getShowInfo());
        ShowInfo showInfo = result.orElseThrow();
        showInfo.changeShowPoster(showInfoDTO.getShowPoster());
        showInfo.changeShowName(showInfoDTO.getShowName());
        showInfo.changeShowExplain(showInfoDTO.getShowExplain());
        showInfo.changeShowCategory(showInfoDTO.getShowCategory());
        showInfo.changeShowAge(showInfoDTO.getShowAge());
        showInfo.changeShowDuration(showInfoDTO.getShowDuration());
        ShowLocation showLocation = modelMapper.map(showInfoDTO.getShowLocationDTO(), ShowLocation.class);
        showInfo.changeShowLocation(showLocation);
        showInfo.changeShowStyUrl1(showInfoDTO.getShowStyUrl1());
        showInfo.changeShowStyUrl2(showInfoDTO.getShowStyUrl2());
        showInfo.changeShowStyUrl3(showInfoDTO.getShowStyUrl3());
        showInfo.changeShowStyUrl4(showInfoDTO.getShowStyUrl4());
        showInfoRepository.save(showInfo);
    }

    @Override
    public void modify(ShowLocationDTO showLocationDTO) {
        Optional<ShowLocation> result = showLocationRepository.findById(showLocationDTO.getShowlocation());
        ShowLocation showLocation = result.orElseThrow();
        showLocation.changeLocationName(showLocationDTO.getLocationName());
        showLocation.changeLocationAddress(showLocationDTO.getLocationAddress());
        showLocationRepository.save(showLocation);
    }

    @Override
    public void removeShow(Integer tno) {}

    @Override
    public void removeShowInfo(Integer tno) {}

    @Override
    public void removeShowLocation(Integer tno) {}

    @Override
    public PageResponseDTO<ShowDTO> showList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("showNo").descending());

        Page<Show> result;
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        if (type != null && keyword != null && !keyword.isBlank()) {
            if (type.equals("t")) {
                result = showRepository.findByShowInfo_ShowNameContaining(keyword, pageable);
            } else {
                result = showRepository.findAll(pageable);
            }
        } else {
            result = showRepository.findAll(pageable);
        }

        List<ShowDTO> dtoList = result.getContent().stream()
            .map(show -> modelMapper.map(show, ShowDTO.class))
            .collect(Collectors.toList());

        return PageResponseDTO.<ShowDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(result.getTotalElements())
            .build();
    }

    @Override
    public PageResponseDTO<ShowInfoDTO> showInfoList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("showInfo").descending());

        Page<ShowInfo> result;
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        if (type != null && keyword != null && !keyword.isBlank()) {
            if (type.equals("t")) {
                result = showInfoRepository.findByShowNameContaining(keyword, pageable);
            } else {
                result = showInfoRepository.findAll(pageable);
            }
        } else {
            result = showInfoRepository.findAll(pageable);
        }

        List<ShowInfoDTO> dtoList = result.getContent().stream()
            .map(showInfo -> modelMapper.map(showInfo, ShowInfoDTO.class))
            .collect(Collectors.toList());

        return PageResponseDTO.<ShowInfoDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(result.getTotalElements())
            .build();
    }

    @Override
    public PageResponseDTO<ShowLocationDTO> showLocationList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("showlocation").descending());

        Page<ShowLocation> result;
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        if (type != null && keyword != null && !keyword.isBlank()) {
            if (type.equals("l")) {
                result = showLocationRepository.findByLocationNameContaining(keyword, pageable);
            } else {
                result = showLocationRepository.findAll(pageable);
            }
        } else {
            result = showLocationRepository.findAll(pageable);
        }

        List<ShowLocationDTO> dtoList = result.getContent().stream()
            .map(showLocation -> modelMapper.map(showLocation, ShowLocationDTO.class))
            .collect(Collectors.toList());

        return PageResponseDTO.<ShowLocationDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(result.getTotalElements())
            .build();
    }
    
    @Override
    public int getTotalCount() {
        return (int) showRepository.count();  // 전체 Show 엔티티 수
    }

}
