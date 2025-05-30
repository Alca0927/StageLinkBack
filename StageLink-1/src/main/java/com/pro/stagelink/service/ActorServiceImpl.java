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
import com.pro.stagelink.repository.ActorRepository;
import com.pro.stagelink.repository.ActorShowRepository;
import com.pro.stagelink.repository.ShowInfoRepository;

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

    // 배우 등록
    @Override
    public int register(ActorDTO actorDTO) {
        Actor actor = modelMapper.map(actorDTO, Actor.class);
        Actor savedActor = actorRepository.save(actor);
        return savedActor.getActorNo();
    }

    // 배우-공연 등록
    @Override
    public int register(ActorShowDTO actorShowDTO) {
        ActorShow actorShow = modelMapper.map(actorShowDTO, ActorShow.class);
        ActorShow savedActorShow = actorShowRepository.save(actorShow);
        return savedActorShow.getActor().getActorNo();
    }

    // 배우 조회
    @Override
    public ActorDTO getActor(int actorNo) {
        Optional<Actor> result = actorRepository.findById(actorNo);
        Actor actor = result.orElseThrow();
        return modelMapper.map(actor, ActorDTO.class);
    }

    // 배우-공연 조회
    @Override
    public ActorShowDTO getActorShow(int actorNo, int showInfoId) {
        Actor actor = actorRepository.findById(actorNo)
            .orElseThrow(() -> new RuntimeException("배우가 존재하지 않습니다: " + actorNo));

        ShowInfo showInfo = showInfoRepository.findById(showInfoId)
            .orElseThrow(() -> new RuntimeException("공연 정보가 존재하지 않습니다: " + showInfoId));

        Optional<ActorShow> result = actorShowRepository.findByActorAndShowInfo(actor, showInfo);
        ActorShow actorShow = result.orElseThrow(() ->
            new RuntimeException("배우-공연 매칭 정보가 존재하지 않습니다."));

        return modelMapper.map(actorShow, ActorShowDTO.class);
    }

    // 배우 수정
    @Override
    public void modify(ActorDTO actorDTO) {
        Optional<Actor> result = actorRepository.findById(actorDTO.getActorNo());
        Actor actor = result.orElseThrow();

        actor.changeActorImage(actorDTO.getActorImage());
        actor.changeActorName(actorDTO.getActorName());
        actor.changeActorProfile(actorDTO.getActorProfile());

        actorRepository.save(actor);
    }

    // 배우-공연 수정
    @Override
    public void modify(ActorShowDTO actorShowDTO) {
        Actor actor = actorRepository.findById(actorShowDTO.getActorDTO().getActorNo())
            .orElseThrow(() -> new RuntimeException("배우가 존재하지 않습니다: " + actorShowDTO.getActorDTO().getActorNo()));

        ShowInfo showInfo = showInfoRepository.findById(actorShowDTO.getShowInfoDTO().getShowInfo())
            .orElseThrow(() -> new RuntimeException("공연 정보가 존재하지 않습니다: " + actorShowDTO.getShowInfoDTO().getShowInfo()));

        Optional<ActorShow> result = actorShowRepository.findByActorAndShowInfo(actor, showInfo);
        ActorShow actorShow = result.orElseThrow(() ->
            new RuntimeException("배우-공연 매칭 정보를 찾을 수 없습니다."));

        if (actorShowDTO.getRoleName() != null) {
            actorShow.changeRoleName(actorShowDTO.getRoleName());
        }
        if (actorShowDTO.getShowStartTime() != null) {
            actorShow.changeShowStartTime(actorShowDTO.getShowStartTime());
        }
        if (actorShowDTO.getShowEndTime() != null) {
            actorShow.changeShowEndTime(actorShowDTO.getShowEndTime());
        }

        actorShowRepository.save(actorShow);
    }

    // 배우 목록
    @Override
    public PageResponseDTO<ActorDTO> ActorList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
            pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize(),
            Sort.by("actorNo").descending()
        );

        Page<Actor> result;

        // 🔍 이름으로 검색
        String keyword = pageRequestDTO.getName();
        if (keyword != null && !keyword.trim().isEmpty()) {
            result = actorRepository.findByActorNameContaining(keyword.trim(), pageable);
        } else {
            result = actorRepository.findAll(pageable);
        }

        List<ActorDTO> dtoList = result.getContent().stream()
            .map(actor -> modelMapper.map(actor, ActorDTO.class))
            .collect(Collectors.toList());

        return PageResponseDTO.<ActorDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(result.getTotalElements())
            .build();
    }

    // 배우-공연 목록 (검색 포함)
    @Override
    public PageResponseDTO<ActorShowDTO> ActorShowList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
            pageRequestDTO.getPage() - 1,
            pageRequestDTO.getSize(),
            Sort.by("actor.actorNo").descending()
        );

        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        Page<ActorShow> result;

        if (type != null && keyword != null && !keyword.trim().isEmpty()) {
            if (type.equals("actor")) {
                result = actorShowRepository.findByActor_ActorNameContaining(keyword.trim(), pageable);
            } else if (type.equals("show")) {
                result = actorShowRepository.findByShowInfo_ShowNameContaining(keyword.trim(), pageable);
            } else {
                result = actorShowRepository.findAll(pageable);
            }
        } else {
            result = actorShowRepository.findAll(pageable);
        }

        List<ActorShowDTO> dtoList = result.getContent().stream()
            .map(actorShow -> modelMapper.map(actorShow, ActorShowDTO.class))
            .collect(Collectors.toList());

        return PageResponseDTO.<ActorShowDTO>withAll()
            .dtoList(dtoList)
            .pageRequestDTO(pageRequestDTO)
            .totalCount(result.getTotalElements())
            .build();
    }
    
    @Override
    public int getTotalCount() {
        return (int) actorRepository.count();
    }


}
