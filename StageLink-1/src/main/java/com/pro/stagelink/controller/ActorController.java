package com.pro.stagelink.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.dto.ActorDTO;
import com.pro.stagelink.dto.ActorShowDTO;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.service.ActorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class ActorController {
	private final ActorService actorService;
	
	// 배우 목록 ------------------------------------------------
	@GetMapping("/showmanager/actor/list")
	public PageResponseDTO<ActorDTO> actorList(PageRequestDTO pageRequestDTO){
		log.info("-------------------actor list------------------");
		return actorService.ActorList(pageRequestDTO);
	}
	
	// 배우 조회 ------------------------------------------------
	@GetMapping("/showmanager/actor/{actorNo}")
	public ActorDTO getActor(@PathVariable(name="actorNo") int actorNo) {
		return actorService.getActor(actorNo);
	}
	
	// 배우 등록 ------------------------------------------------
	@PostMapping("/showmanager/actor/add")
	public Map<String, Integer> registerActor(@RequestBody ActorDTO actorDTO){
		log.info("actorDTO : " + actorDTO);
		int actorNo = actorService.register(actorDTO);
		return Map.of("actorNo", actorNo);
	}
	
	// 배우 수정 ------------------------------------------------
	@PutMapping("/showmanager/actor/{actorNo}")
	public Map<String, String> modifyActor(@PathVariable(name = "actorNo") int actorNo, @RequestBody ActorDTO actorDTO){
		actorDTO.setActorNo(actorNo);
		log.info("Modify : " + actorDTO);
		actorService.modify(actorDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	// ------------------------------------------------------------------------
	// 배우 출연작 목록
	@GetMapping("/showmanager/actorshow/list")
	public PageResponseDTO<ActorShowDTO> actorShowList(PageRequestDTO pageRequestDTO){
		log.info("-------------------actor list------------------");
		return actorService.ActorShowList(pageRequestDTO);
	}
	
	// 배우 출연작 조회 ------------------------------------------------
	@GetMapping("/showmanager/actorshow/{actorNo}/{showInfoId}")
	public ActorShowDTO getActorShow(@PathVariable(name="actorNo") int actorNo,@PathVariable(name="showInfoId") int showInfoId) {
		return actorService.getActorShow(actorNo, showInfoId);
	}
	
	// 배우 출연작 등록 ------------------------------------------------
	@PostMapping("/showmanager/actorshow/add")
	public Map<String, Integer> registerActorShow(@RequestBody ActorShowDTO actorShowDTO){
		log.info("actorShowDTO : " + actorShowDTO);
		int actorNo = actorService.register(actorShowDTO);
		return Map.of("actorNo", actorNo);
	}
		
	// 배우 출연작 수정 ------------------------------------------------
	@PutMapping("/showmanager/actorshow/{actorNo}/{showInfoId}")
	public Map<String, String> modifyActorShow(
	    @PathVariable(name = "actorNo") int actorNo,
	    @PathVariable(name = "showInfoId") int showInfoId, 
	    @RequestBody ActorShowDTO actorShowDTO){
	    
	    // PathVariable로 받은 값들을 DTO에 설정
	    // 기존 ActorDTO와 ShowInfoDTO 정보 유지 또는 새로 설정
	    if (actorShowDTO.getActorDTO() == null) {
	        ActorDTO actorDTO = new ActorDTO();
	        actorDTO.setActorNo(actorNo);
	        actorShowDTO.setActorDTO(actorDTO);
	    } else {
	        actorShowDTO.getActorDTO().setActorNo(actorNo);
	    }
	    
	    if (actorShowDTO.getShowInfoDTO() == null) {
	        ShowInfoDTO showInfoDTO = new ShowInfoDTO();
	        showInfoDTO.setShowInfo(showInfoId);
	        actorShowDTO.setShowInfoDTO(showInfoDTO);
	    } else {
	        actorShowDTO.getShowInfoDTO().setShowInfo(showInfoId);
	    }
	    
	    log.info("Modify ActorShow : " + actorShowDTO);
	    actorService.modify(actorShowDTO);
	    
	    return Map.of("RESULT","SUCCESS");
	}
}
