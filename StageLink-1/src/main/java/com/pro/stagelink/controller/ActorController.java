package com.pro.stagelink.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.pro.stagelink.dto.*;
import com.pro.stagelink.service.ActorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class ActorController {
    private final ActorService actorService;

    // 배우 목록
    @GetMapping("/showmanager/actor/list")
    public PageResponseDTO<ActorDTO> actorList(PageRequestDTO pageRequestDTO) {
        log.info("actor list");
        return actorService.ActorList(pageRequestDTO);
    }

    // 배우 조회
    @GetMapping("/showmanager/actor/{actorNo}")
    public ActorDTO getActor(@PathVariable("actorNo") int actorNo) {
        return actorService.getActor(actorNo);
    }

    // 배우 등록
    @PostMapping("/showmanager/actor/add")
    public Map<String, Integer> registerActor(@RequestBody ActorDTO actorDTO) {
        log.info("actorDTO : " + actorDTO);
        int actorNo = actorService.register(actorDTO);
        return Map.of("actorNo", actorNo);
    }

    // 배우 수정 (파일 포함)
    @PutMapping("/showmanager/actor/{actorNo}")
    public Map<String, String> modifyActor(
        @PathVariable("actorNo") int actorNo,
        @RequestParam("actorName") String actorName,
        @RequestParam("actorProfile") String actorProfile,
        @RequestPart(value = "actorImage", required = false) MultipartFile actorImage
    ) {
        log.info("Modify actorNo={}, name={}, profile={}", actorNo, actorName, actorProfile);

        String imageUrl = null;
        if (actorImage != null && !actorImage.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + actorImage.getOriginalFilename();
                Path uploadPath = Paths.get("src/main/resources/static/images");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(actorImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imageUrl = "/images/" + fileName;
            } catch (IOException e) {
                log.error("이미지 업로드 실패", e);
                throw new RuntimeException("파일 저장 오류");
            }
        }

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setActorNo(actorNo);
        actorDTO.setActorName(actorName);
        actorDTO.setActorProfile(actorProfile);
        if (imageUrl != null) {
            actorDTO.setActorImage(imageUrl);
        }

        actorService.modify(actorDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    // 배우 출연작 목록
    @GetMapping("/showmanager/actorshow/list")
    public PageResponseDTO<ActorShowDTO> actorShowList(PageRequestDTO pageRequestDTO) {
        log.info("actor show list");
        return actorService.ActorShowList(pageRequestDTO);
    }

    // 배우 출연작 조회
    @GetMapping("/showmanager/actorshow/{actorNo}/{showInfoId}")
    public ActorShowDTO getActorShow(@PathVariable("actorNo") int actorNo, @PathVariable("showInfoId") int showInfoId) {
        return actorService.getActorShow(actorNo, showInfoId);
    }

    // 배우 출연작 등록
    @PostMapping("/showmanager/actorshow/add")
    public Map<String, Integer> registerActorShow(@RequestBody ActorShowDTO actorShowDTO) {
        log.info("actorShowDTO : " + actorShowDTO);
        int actorNo = actorService.register(actorShowDTO);
        return Map.of("actorNo", actorNo);
    }

    // 배우 출연작 수정
    @PutMapping("/showmanager/actorshow/{actorNo}/{showInfoId}")
    public Map<String, String> modifyActorShow(
        @PathVariable("actorNo") int actorNo,
        @PathVariable("showInfoId") int showInfoId,
        @RequestBody ActorShowDTO actorShowDTO
    ) {
        if (actorShowDTO.getActorDTO() == null) {
            actorShowDTO.setActorDTO(new ActorDTO());
        }
        actorShowDTO.getActorDTO().setActorNo(actorNo);

        if (actorShowDTO.getShowInfoDTO() == null) {
            actorShowDTO.setShowInfoDTO(new ShowInfoDTO());
        }
        actorShowDTO.getShowInfoDTO().setShowInfo(showInfoId);

        log.info("Modify ActorShow : " + actorShowDTO);
        actorService.modify(actorShowDTO);

        return Map.of("RESULT", "SUCCESS");
    }
}
