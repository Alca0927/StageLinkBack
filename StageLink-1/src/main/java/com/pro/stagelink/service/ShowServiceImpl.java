package com.pro.stagelink.service;

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
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.dto.ShowLocationDTO;
import com.pro.stagelink.repository.ShowInfoRepository;
import com.pro.stagelink.repository.ShowLocationRepository;
import com.pro.stagelink.repository.ShowRepository;

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
	
	// 등록----------------------------------
	@Override
	public Integer register(ShowDTO showDTO) {
		log.info("-----show---------");
		
		Show show = modelMapper.map(showDTO, Show.class);
		Show savedShow = showRepository.save(show);
		return savedShow.getShowNo();
	}

	@Override
	public int register(ShowInfoDTO showInfoDTO) {
		log.info("------showInfo--------");
		
		ShowInfo showInfo = modelMapper.map(showInfoDTO, ShowInfo.class);
		ShowInfo savedShowInfo = showInfoRepository.save(showInfo);
		return savedShowInfo.getShowInfo();
	}

	@Override
	public int register(ShowLocationDTO showLocationDTO) {
		log.info("-----ShowLocation---------");
		
		ShowLocation showLocation = modelMapper.map(showLocationDTO, ShowLocation.class);
		ShowLocation savedShowLocation = showLocationRepository.save(showLocation);
		return savedShowLocation.getShowLocation();
	}

	// 조회-----------------------------
	@Override
	public ShowDTO getShow(int tno) {
		java.util.Optional<Show> result = showRepository.findById(tno);
		
		Show show = result.orElseThrow();
		ShowDTO dto = modelMapper.map(show, ShowDTO.class);
		
		return dto;
	}

	@Override
	public ShowInfoDTO getShowInfo(int tno) {
		java.util.Optional<ShowInfo> result = showInfoRepository.findById(tno);
		
		ShowInfo showInfo = result.orElseThrow();
		ShowInfoDTO dto = modelMapper.map(showInfo, ShowInfoDTO.class);
		
		return dto;
	}

	@Override
	public ShowLocationDTO getShowLocation(int tno) {
		java.util.Optional<ShowLocation> result = showLocationRepository.findById(tno);
		
		ShowLocation showLocation = result.orElseThrow();
		ShowLocationDTO dto = modelMapper.map(showLocation, ShowLocationDTO.class);
		
		return dto;
	}

	
	// 수정-------------------------------
	@Override
	public void modify(ShowDTO showDTO) {
		Optional<Show> result = showRepository.findById(showDTO.getShowNo());
		
		Show show = result.orElseThrow();
		
		// DTO → Entity 변환 (GPT )
	    ShowInfo showInfo = modelMapper.map(showDTO.getShowInfoDTO(), ShowInfo.class);
		show.changeShowInfo(showInfo);
		show.changeShowStartTime(showDTO.getShowStartTime());
		show.changeShowEndTime(showDTO.getShowEndTime());
		show.changeSeatRPrice(showDTO.getSeatRPrice());
		show.changeSeatAPrice(showDTO.getSeatAPrice());
		show.changeSeatSPrice(showDTO.getSeatSPrice());
		show.changeSeatVipPrice(showDTO.getSeatVipPrice());
		show.changeSeatRCount(showDTO.getSeatRCount());
		show.changeSeatACount(showDTO.getSeatRCount());
		show.changeSeatSCount(showDTO.getSeatRCount());
		show.changeSeatVipCount(showDTO.getSeatRCount());
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
		
		// DTO → Entity 변환 (GPT )
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
		Optional<ShowLocation> result = showLocationRepository.findById(showLocationDTO.getShow_location());
		
		ShowLocation showLocation = result.orElseThrow();
		
		showLocation.changeLocation_name(showLocation.getLocationName());
		showLocation.changeLocation_address(showLocationDTO.getLocation_address());
		
		showLocationRepository.save(showLocation);
	}

	
	// 삭제---------------------------
	@Override
	public void removeShow(Integer tno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeShowInfo(Integer tno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeShowLocation(Integer tno) {
		// TODO Auto-generated method stub
		
	}
	
	// 목록 ----------------------------------------------

	@Override
	public PageResponseDTO<ShowDTO> showList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("tno").descending());

		Page<Show> result = showRepository.findAll(pageable);

		List<ShowDTO> dtoList = result.getContent().stream().map(show -> modelMapper.map(show, ShowDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<ShowDTO> responseDTO = PageResponseDTO.<ShowDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}

	@Override
	public PageResponseDTO<ShowInfoDTO> showInfoList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("tno").descending());

		Page<ShowInfo> result = showInfoRepository.findAll(pageable);

		List<ShowInfoDTO> dtoList = result.getContent().stream().map(showInfo -> modelMapper.map(showInfo, ShowInfoDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<ShowInfoDTO> responseDTO = PageResponseDTO.<ShowInfoDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}

	@Override
	public PageResponseDTO<ShowLocationDTO> showLocationList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("tno").descending());

		Page<ShowLocation> result = showLocationRepository.findAll(pageable);

		List<ShowLocationDTO> dtoList = result.getContent().stream().map(showLocation -> modelMapper.map(showLocation, ShowLocationDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<ShowLocationDTO> responseDTO = PageResponseDTO.<ShowLocationDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}

}
