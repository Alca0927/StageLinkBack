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

import com.pro.stagelink.domain.Actor;
import com.pro.stagelink.domain.ActorShow;
import com.pro.stagelink.domain.ShowInfo;
import com.pro.stagelink.dto.ActorDTO;
import com.pro.stagelink.dto.ActorShowDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.repository.ActorRepository;
import com.pro.stagelink.repository.ActorShowRepository;
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
public class ActorServiceImpl implements ActorService {
	private final ModelMapper modelMapper;
	private final ActorRepository actorRepository;
	private final ActorShowRepository actorShowRepository;
	private final ShowInfoRepository showInfoRepository;
	
	// 등록----------------------------------
	@Override
	public int register(ActorDTO ActorDTO) {
		log.info("--------Actor--------");
		
		Actor actor = modelMapper.map(ActorDTO, Actor.class);
		Actor savedActor = actorRepository.save(actor);
		
		return savedActor.getActorNo();
	}

	@Override
	public int register(ActorShowDTO ActorShowDTO) {

		ActorShow actorShow = modelMapper.map(ActorShowDTO, ActorShow.class);
		ActorShow savedActorShow = actorShowRepository.save(actorShow);
		
		return savedActorShow.getActor().getActorNo();
	}

	// 조회----------------------------------
	@Override
	public ActorDTO getActor(int actorNo) {
		Optional<Actor> result = actorRepository.findById(actorNo);
		Actor actor = result.orElseThrow();
		ActorDTO dto = modelMapper.map(actor, ActorDTO.class);
		return dto;
	}

	@Override
	public ActorShowDTO getActorShow(int actorNo, int showInfoId) {
		/*
		Optional<ActorShow> result = actorShowRepository.findByActorAndShowInfo(actorNo, showInfoId);
		ActorShow actorShow = result.orElseThrow();
		ActorShowDTO dto = modelMapper.map(actorShow, ActorShowDTO.class);
		return dto;
		*/
		Actor actor = actorRepository.findById(actorNo)
		        .orElseThrow(() -> new RuntimeException("배우가 존재하지 않습니다: " + actorNo));
		    
		    ShowInfo showInfo = showInfoRepository.findById(showInfoId)
		        .orElseThrow(() -> new RuntimeException("공연 정보가 존재하지 않습니다: " + showInfoId));
		    
		    // ActorShow 조회
		    Optional<ActorShow> result = actorShowRepository.findByActorAndShowInfo(actor, showInfo);
		    ActorShow actorShow = result.orElseThrow(() -> 
		        new RuntimeException("배우-공연 매칭 정보가 존재하지 않습니다."));

		    // DTO로 변환해서 반환
		    return modelMapper.map(actorShow, ActorShowDTO.class);
	}

	
	// 수정----------------------------------
	@Override
	public void modify(ActorDTO actorDTO) {
		Optional<Actor> result = actorRepository.findById(actorDTO.getActorNo());
		
		Actor actor = result.orElseThrow();
		
		actor.changeActorImage(actorDTO.getActorImage());
		actor.changeActorName(actorDTO.getActorName());
		actor.changeActorProfile(actorDTO.getActorProfile());
		
		actorRepository.save(actor);
	}

	@Override
	public void modify(ActorShowDTO actorShowDTO) {
		/*
		Optional<ActorShow> result = actorShowRepository.findByActorAndShowInfo(actorShowDTO.getActorDTO(), actorShowDTO.getShowInfoDTO());
		ActorShow actorShow = result.orElseThrow();
		
		actorShow.changeRoleName(actorShowDTO.getRoleName());
		
		ShowInfo showInfo = modelMapper.map(actorShowDTO.getShowInfoDTO(), ShowInfo.class);
		actorShow.changeShowInfo(showInfo);
		actorShow.changeShowStartTime(actorShowDTO.getShowStartTime());
		actorShow.changeShowEndTime(actorShowDTO.getShowEndTime());
		
		actorShowRepository.save(actorShow);
		 */
		// DTO → Entity 변환
	    Actor actor = modelMapper.map(actorShowDTO.getActorDTO(), Actor.class);
	    ShowInfo showInfo = modelMapper.map(actorShowDTO.getShowInfoDTO(), ShowInfo.class);
	    
	    // 기존 ActorShow 조회
	    Optional<ActorShow> result = actorShowRepository.findByActorAndShowInfo(actor, showInfo);
	    ActorShow actorShow = result.orElseThrow(() -> 
	        new RuntimeException("배우-공연 매칭 정보를 찾을 수 없습니다.")
	    );
	    
	    // 값 변경
	    actorShow.changeRoleName(actorShowDTO.getRoleName());
	    actorShow.changeShowInfo(showInfo); // showInfo 자체도 변경
	    actorShow.changeShowStartTime(actorShowDTO.getShowStartTime());
	    actorShow.changeShowEndTime(actorShowDTO.getShowEndTime());

	    // 저장
	    actorShowRepository.save(actorShow);
	}

	// 목록----------------------------------
	@Override
	public PageResponseDTO<ActorDTO> ActorList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("actorNo").descending());

		Page<Actor> result = actorRepository.findAll(pageable);

		List<ActorDTO> dtoList = result.getContent().stream().map(actor -> modelMapper.map(actor, ActorDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<ActorDTO> responseDTO = PageResponseDTO.<ActorDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}

	@Override
	public PageResponseDTO<ActorShowDTO> ActorShowList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("actor.actorNo").descending());

		Page<ActorShow> result = actorShowRepository.findAll(pageable);

		List<ActorShowDTO> dtoList = result.getContent().stream().map(actorShow -> modelMapper.map(actorShow, ActorShowDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<ActorShowDTO> responseDTO = PageResponseDTO.<ActorShowDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}
}

