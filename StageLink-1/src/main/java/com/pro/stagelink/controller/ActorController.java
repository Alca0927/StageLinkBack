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
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.service.ActorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class ActorController {
	private final ActorService actorService;
	
	// 목록 ------------------------------------------------
	@GetMapping("/showmanager/actor/list")
	public PageResponseDTO<ActorDTO> actorList(PageRequestDTO pageRequestDTO){
		log.info("-------------------actor list------------------");
		return actorService.ActorList(pageRequestDTO);
	}
	
	// 조회 ------------------------------------------------
	@GetMapping("/showmanager/actor/{actorNo}")
	public ActorDTO getActor(@PathVariable(name="actorNo") int actorNo) {
		return actorService.getActor(actorNo);
	}
	
	// 등록 ------------------------------------------------
	@PostMapping("/showmanager/actor/add")
	public Map<String, Integer> register(@RequestBody ActorDTO actorDTO){
		log.info("actorDTO : " + actorDTO);
		int actorNo = actorService.register(actorDTO);
		return Map.of("actorNo", actorNo);
	}
	
	// 수정 ------------------------------------------------
	@PutMapping("/showmanager/actor/{actorNo}")
	public Map<String, String> modify(@PathVariable(name = "actorNo") int actorNo, @RequestBody ActorDTO actorDTO){
		actorDTO.setActorNo(actorNo);
		log.info("Modify : " + actorDTO);
		actorService.modify(actorDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	
	
}
