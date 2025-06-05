package com.pro.stagelink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pro.stagelink.domain.ShowInfo;
import com.pro.stagelink.domain.ShowLocation;
import com.pro.stagelink.repository.ShowInfoRepository;
import com.pro.stagelink.repository.ShowLocationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class KopisService {
    private final ShowLocationRepository locationRepository;
    private final ShowInfoRepository infoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String serviceKey = "API_key"; // 본인 키 입력 

    public void fetchAndSaveData() {
        log.info("데이터 수집 시작");
        
        // 1. 공연시설 정보 수집 및 저장
        List<String> facilityIds = fetchFacilityIds();
        log.info("수집된 공연시설 수: {}", facilityIds.size());
        saveShowLocations(facilityIds);

        // 2. 공연 정보 수집 및 저장
        List<String> showIds = fetchShowIds();
        log.info("수집된 공연 수: {}", showIds.size());
        saveShowInfos(showIds);
        
        log.info("데이터 수집 완료");
    }

    // 1. 공연시설 목록 → mt10id 수집 (대폭 증가)
    private List<String> fetchFacilityIds() {
        List<String> ids = new ArrayList<>();
        int page = 1;
        boolean hasMoreData = true;
        
        while (hasMoreData && page <= 10) { // 최대 100페이지까지 수집
            try {
                String url = String.format("http://www.kopis.or.kr/openApi/restful/prfplc?service=%s&cpage=%d&rows=10", serviceKey, page);
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
                
                List<Element> dbElements = doc.select("db");
                if (dbElements.isEmpty()) {
                    hasMoreData = false;
                    break;
                }
                
                for (Element e : dbElements) {
                    Element mt10idElement = e.selectFirst("mt10id");
                    if (mt10idElement != null) {
                        ids.add(mt10idElement.text());
                    }
                }
                
                log.info("공연시설 페이지 {} 처리 완료, 현재까지 수집: {}개", page, ids.size());
                page++;
                
                // API 호출 제한을 위한 잠시 대기
                Thread.sleep(100);
                
            } catch (Exception e) {
                log.error("공연시설 페이지 {} 처리 중 오류: {}", page, e.getMessage());
                page++;
            }
        }
        
        return ids;
    }

    // 2. 공연시설 상세 저장
    private void saveShowLocations(List<String> facilityIds) {
        int savedCount = 0;
        for (String id : facilityIds) {
            try {
                // 이미 저장된 공연시설인지 확인
                if (locationRepository.findByFacilityId(id).isPresent()) {
                    continue;
                }
                
                String url = String.format("http://www.kopis.or.kr/openApi/restful/prfplc/%s?service=%s", id, serviceKey);
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
                Element e = doc.selectFirst("db");
                
                if (e != null) {
                    Element nameElement = e.selectFirst("fcltynm");
                    Element addressElement = e.selectFirst("adres");
                    
                    if (nameElement != null && addressElement != null) {
                        String name = nameElement.text();
                        String address = addressElement.text();

                        ShowLocation location = ShowLocation.builder()
                                .facilityId(id)
                                .locationName(name)
                                .locationAddress(address)
                                .build();
                        locationRepository.save(location);
                        savedCount++;
                        
                        if (savedCount % 10 == 0) {
                            log.info("공연시설 {}개 저장 완료", savedCount);
                        }
                    }
                }
                
                // API 호출 제한을 위한 잠시 대기
                Thread.sleep(50);
                
            } catch (Exception e) {
                log.error("공연시설 {} 저장 중 오류: {}", id, e.getMessage());
            }
        }
        log.info("총 {}개 공연시설 저장 완료", savedCount);
    }

    // 3. 공연ID 목록 수집 (대폭 증가)
    private List<String> fetchShowIds() {
        List<String> ids = new ArrayList<>();
        int page = 1;
        boolean hasMoreData = true;
        
        while (hasMoreData && page <= 10) { // 최대 500페이지까지 수집
            try {
                String url = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=20150101&eddate=20250601&cpage=%d&rows=10", serviceKey, page);
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
                
                List<Element> dbElements = doc.select("db");
                if (dbElements.isEmpty()) {
                    hasMoreData = false;
                    break;
                }
                
                for (Element e : dbElements) {
                    Element mt20idElement = e.selectFirst("mt20id");
                    if (mt20idElement != null) {
                        ids.add(mt20idElement.text());
                    }
                }
                
                log.info("공연 페이지 {} 처리 완료, 현재까지 수집: {}개", page, ids.size());
                page++;
                
                // API 호출 제한을 위한 잠시 대기
                Thread.sleep(100);
                
            } catch (Exception e) {
                log.error("공연 페이지 {} 처리 중 오류: {}", page, e.getMessage());
                page++;
            }
        }
        
        return ids;
    }

    // 4. 공연상세 저장 (개선된 로직)
    private void saveShowInfos(List<String> showIds) {
        int savedCount = 0;
        int skippedCount = 0;
        
        for (String id : showIds) {
            try {
                // 공연 상세 정보를 먼저 가져와서 중복 체크용 데이터 확인
                
                String url = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s", id, serviceKey);
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
                Element e = doc.selectFirst("db");

                if (e != null) {
                    // 필수 정보 먼저 확인
                    Element prfnmElement = e.selectFirst("prfnm");
                    Element posterElement = e.selectFirst("poster");
                    Element mt10idElement = e.selectFirst("mt10id");
                    
                    if (prfnmElement == null || posterElement == null || mt10idElement == null) {
                        log.warn("공연 {}에 필수 정보가 없습니다", id);
                        skippedCount++;
                        continue;
                    }
                    
                    String showName = prfnmElement.text();
                    String showPoster = posterElement.text();
                    String mt10id = mt10idElement.text();
                    
                    // 공연명과 포스터로 중복 체크 (이미 같은 공연이 있는지 확인)
                    if (infoRepository.existsByShowNameAndShowPoster(showName, showPoster)) {
                        skippedCount++;
                        continue;
                    }
                    Optional<ShowLocation> locationOpt = locationRepository.findByFacilityId(mt10id);

                    // 공연장 정보가 없으면 새로 추가
                    if (!locationOpt.isPresent()) {
                        ShowLocation newLocation = fetchAndSaveNewLocation(mt10id);
                        if (newLocation != null) {
                            locationOpt = Optional.of(newLocation);
                            log.info("새로운 공연장 추가: {} ({})", newLocation.getLocationName(), mt10id);
                        } else {
                            log.warn("공연장 정보를 가져올 수 없어 공연 {} 건너뜀", id);
                            skippedCount++;
                            continue;
                        }
                    }



                    // 관람 연령 처리
                    String ageText = e.selectFirst("prfage") != null ? e.selectFirst("prfage").text() : "0";
                    int showAge = extractAgeNumber(ageText);

                    // 이미지 URL 분리 처리
                    String urls = e.selectFirst("styurls") != null ? e.selectFirst("styurls").text() : "";
                    String[] urlParts = parseImageUrls(urls);

                    ShowInfo info = ShowInfo.builder()
                            .showPoster(showPoster)
                            .showName(showName)
                            .showExplain(e.selectFirst("sty") != null ? e.selectFirst("sty").text() : "")
                            .showCategory(e.selectFirst("genrenm") != null ? e.selectFirst("genrenm").text() : "")
                            .showAge(showAge)
                            .showDuration(e.selectFirst("prfruntime") != null ? e.selectFirst("prfruntime").text() : "")
                            .showLocation(locationOpt.get())
                            .showStyUrl1(urlParts.length > 0 ? urlParts[0] : null)
                            .showStyUrl2(urlParts.length > 1 ? urlParts[1] : null)
                            .showStyUrl3(urlParts.length > 2 ? urlParts[2] : null)
                            .showStyUrl4(urlParts.length > 3 ? urlParts[3] : null)
                            .build();

                    infoRepository.save(info);
                    savedCount++;
                    
                    if (savedCount % 50 == 0) {
                        log.info("공연 정보 {}개 저장 완료", savedCount);
                    }
                }
                
                // API 호출 제한을 위한 잠시 대기
                Thread.sleep(50);
                
            } catch (Exception e) {
                log.error("공연 {} 저장 중 오류: {}", id, e.getMessage());
                skippedCount++;
            }
        }
        
        log.info("공연 정보 저장 완료 - 저장: {}개, 건너뜀: {}개", savedCount, skippedCount);
    }

    // 새로운 공연장 정보를 가져와서 저장
    private ShowLocation fetchAndSaveNewLocation(String facilityId) {
        try {
            String url = String.format("http://www.kopis.or.kr/openApi/restful/prfplc/%s?service=%s", facilityId, serviceKey);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
            Element e = doc.selectFirst("db");
            
            if (e != null) {
                Element nameElement = e.selectFirst("fcltynm");
                Element addressElement = e.selectFirst("adres");
                
                if (nameElement != null && addressElement != null) {
                    String name = nameElement.text();
                    String address = addressElement.text();

                    ShowLocation location = ShowLocation.builder()
                            .facilityId(facilityId)
                            .locationName(name)
                            .locationAddress(address)
                            .build();
                    
                    return locationRepository.save(location);
                }
            }
        } catch (Exception e) {
            log.error("새로운 공연장 {} 저장 중 오류: {}", facilityId, e.getMessage());
        }
        
        return null;
    }

    // 이미지 URL 파싱 개선
    private String[] parseImageUrls(String urls) {
        if (urls == null || urls.trim().isEmpty()) {
            return new String[0];
        }
        
        List<String> urlList = new ArrayList<>();
        String[] parts = urls.split("http://");
        
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                String fullUrl = "http://" + part.trim();
                // URL 유효성 간단 체크
                if (fullUrl.contains(".")) {
                    urlList.add(fullUrl);
                }
            }
        }
        
        return urlList.toArray(new String[0]);
    }

    // 관람연령에서 숫자 추출 함수
    private int extractAgeNumber(String ageText) {
        if (ageText == null || ageText.trim().isEmpty()) {
            return 0;
        }
        
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(ageText);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}