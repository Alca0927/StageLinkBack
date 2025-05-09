package com.pro.stagelink.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.ShowDTO;
import com.pro.stagelink.dto.ShowInfoDTO;
import com.pro.stagelink.dto.ShowLocationDTO;
import com.pro.stagelink.service.ShowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class ShowController {
	private final ShowService showService;
	
	// 공연 목록
	@GetMapping("/showmanager/showlist")
	public PageResponseDTO<ShowDTO> showList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return showService.showList(pageRequestDTO);
	}
	
	@PostMapping("/showmanager/showlist/add")
	public Map<String, Integer> register(@RequestBody ShowDTO showDTO){
		log.info("showDTO : " + showDTO);
		int tno = showService.register(showDTO);
		return Map.of("TNO:", tno);
	}
	
	@GetMapping("/showmanager/showlist/{tno}")
	public ShowDTO getShow(@PathVariable(name="tno") int tno) {
		return showService.getShow(tno);
	}
	
	@PutMapping("/showmanager/showlist/{tno}")
	public Map<String, String> modify(@PathVariable(name = "tno") int tno, @RequestBody ShowDTO showDTO){
		showDTO.setShowNo(tno);
		log.info("Modify : " + showDTO);
		showService.modify(showDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	// 공연 상세 정보
	@GetMapping("/showmanager/showinfo")
	public PageResponseDTO<ShowInfoDTO> showInfoList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return showService.showInfoList(pageRequestDTO);
	}
		
	@PostMapping("/showmanager/showinfo/add")
	public Map<String, Integer> register(@RequestBody ShowInfoDTO showInfoDTO){
		log.info("showInfoDTO : " + showInfoDTO);
		int tno = showService.register(showInfoDTO);
		return Map.of("TNO:", tno);
	}
		
	@GetMapping("/showmanager/showinfo/{tno}")
	public ShowInfoDTO getShowInfo(@PathVariable(name="tno") int tno) {
		return showService.getShowInfo(tno);
	}
	
	@PutMapping("/showmanager/showinfo/{tno}")
	public Map<String, String> modify(@PathVariable(name = "tno") int tno, @RequestBody ShowInfoDTO showInfoDTO){
		showInfoDTO.setShowInfo(tno);
		log.info("Modify : " + showInfoDTO);
		showService.modify(showInfoDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	// 공연장
	@GetMapping("/showmanager/location")
	public PageResponseDTO<ShowLocationDTO> showLocationList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return showService.showLocationList(pageRequestDTO);
	}
		
	@PostMapping("/showmanager/location/add")
	public Map<String, Integer> register(@RequestBody ShowLocationDTO showLocationDTO){
		log.info("showInfoDTO : " + showLocationDTO);
		int tno = showService.register(showLocationDTO);
		return Map.of("TNO:", tno);
	}
		
	@GetMapping("/showmanager/location/{tno}")
	public ShowLocationDTO getShowLocation(@PathVariable(name="tno") int tno) {
		return showService.getShowLocation(tno);
	}
	
	@PutMapping("/showmanager/location/{tno}")
	public Map<String, String> modify(@PathVariable(name = "tno") int tno, @RequestBody ShowLocationDTO showLocationDTO){
		showLocationDTO.setShow_location(tno);
		log.info("Modify : " + showLocationDTO);
		showService.modify(showLocationDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
}
