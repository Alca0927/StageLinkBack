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
	@GetMapping("/showmanager/show/list")
	public PageResponseDTO<ShowDTO> showList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return showService.showList(pageRequestDTO);
	}
	
	@PostMapping("/showmanager/show/add")
	public Map<String, Integer> register(@RequestBody ShowDTO showDTO){
		log.info("showDTO : " + showDTO);
		int showNo = showService.register(showDTO);
		return Map.of("showNo", showNo);
	}
	
	@GetMapping("/showmanager/show/{showNo}")
	public ShowDTO getShow(@PathVariable(name="showNo") int showNo) {
		return showService.getShow(showNo);
	}
	
	@PutMapping("/showmanager/show/{showNo}")
	public Map<String, String> modify(@PathVariable(name = "showNo") int showNo, @RequestBody ShowDTO showDTO){
		showDTO.setShowNo(showNo);
		log.info("Modify : " + showDTO);
		showService.modify(showDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	// 공연 상세 정보
	@GetMapping("/showmanager/showinfo/list")
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
		
	@GetMapping("/showmanager/showinfo/{showInfo}")
	public ShowInfoDTO getShowInfo(@PathVariable(name="showInfo") int showInfo) {
		return showService.getShowInfo(showInfo);
	}
	
	@PutMapping("/showmanager/showinfo/{showInfo}")
	public Map<String, String> modify(@PathVariable(name = "showInfo") int showInfo, @RequestBody ShowInfoDTO showInfoDTO){
		showInfoDTO.setShowInfo(showInfo);
		log.info("Modify : " + showInfoDTO);
		showService.modify(showInfoDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
	
	// 공연장
	@GetMapping("/showmanager/location/list")
	public PageResponseDTO<ShowLocationDTO> showLocationList(PageRequestDTO pageRequestDTO){
		log.info("---------showlist-----------");
		return showService.showLocationList(pageRequestDTO);
	}
		
	@PostMapping("/showmanager/location/add")
	public Map<String, Integer> register(@RequestBody ShowLocationDTO showLocationDTO){
		log.info("showInfoDTO : " + showLocationDTO);
		int showlocation_id = showService.register(showLocationDTO);
		return Map.of("showlocation_id:", showlocation_id);
	}
		
	@GetMapping("/showmanager/location/{showlocation}")
	public ShowLocationDTO getShowlocation(@PathVariable(name="showlocation") int showlocation) {
		return showService.getShowlocation(showlocation);
	}
	
	@PutMapping("/showmanager/location/{showlocation}")
	public Map<String, String> modify(@PathVariable(name = "showlocation") int showlocation, @RequestBody ShowLocationDTO showLocationDTO){
		showLocationDTO.setShowlocation(showlocation);
		log.info("Modify : " + showLocationDTO);
		showService.modify(showLocationDTO);
		
		return Map.of("RESULT","SUCCESS");
	}
}
