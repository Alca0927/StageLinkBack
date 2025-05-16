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

@Service
@RequiredArgsConstructor
public class KopisService {
	private final ShowLocationRepository locationRepository;
    private final ShowInfoRepository infoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String serviceKey = "e59eb780dd8445ba9778ac0055ff1db2"; // 본인 키 입력

    public void fetchAndSaveData() {
        List<String> facilityIds = fetchFacilityIds();
        saveShowLocations(facilityIds);

        List<String> showIds = fetchShowIds();
        saveShowInfos(showIds);
    }

    // 1. 공연시설 목록 → mt10id 수집
    private List<String> fetchFacilityIds() {
        List<String> ids = new ArrayList<>();
        // 반복적으로 페이지를 돌며 수집
        for (int page = 1; page <= 5; page++) {
            String url = String.format("http://www.kopis.or.kr/openApi/restful/prfplc?service=%s&cpage=%d&rows=10", serviceKey, page);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
            for (Element e : doc.select("db")) {
            	Element mt10idElement = e.selectFirst("mt10id");
                if (mt10idElement != null) {
                    ids.add(mt10idElement.text());
                }
            }
        }
        return ids;
    }

    // 2. 공연시설 상세 저장
    private void saveShowLocations(List<String> facilityIds) {
        for (String id : facilityIds) {
            String url = String.format("http://www.kopis.or.kr/openApi/restful/prfplc/%s?service=%s", id, serviceKey);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
            Element e = doc.selectFirst("db");
            if (e != null) {
                String name = e.selectFirst("fcltynm").text();
                String address = e.selectFirst("adres").text();

                if (!locationRepository.findByFacilityId(id).isPresent()) {
                    ShowLocation location = ShowLocation.builder()
                            .facilityId(id)
                            .locationName(name)
                            .locationAddress(address)
                            .build();
                    locationRepository.save(location);
                }
            }
        }
    }

    // 3. 공연ID 목록 수집
    private List<String> fetchShowIds() {
        List<String> ids = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            String url = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=20240601&eddate=20250101&cpage=%d&rows=10", serviceKey, page);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
            for (Element e : doc.select("db")) {
            	Element mt20idElement = e.selectFirst("mt20id");
                if (mt20idElement != null) {
                    ids.add(mt20idElement.text());
                }
            }
        }
        return ids;
    }

    // 4. 공연상세 저장
    private void saveShowInfos(List<String> showIds) {
        for (String id : showIds) {
            String url = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s", id, serviceKey);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Document doc = Jsoup.parse(response.getBody(), "", Parser.xmlParser());
            Element e = doc.selectFirst("db");

            if (e != null) {
                String mt10id = e.selectFirst("mt10id").text();
                Optional<ShowLocation> locationOpt = locationRepository.findByFacilityId(mt10id);

                if (locationOpt.isPresent()) {
                    // 관람 연령 처리
                    String ageText = e.selectFirst("prfage").text();
                    int showAge = extractAgeNumber(ageText);

                    // 이미지 URL 분리 처리
                    String urls = e.selectFirst("styurls") != null ? e.selectFirst("styurls").text() : "";
                    String[] urlParts = urls.split("http://");
                    for (int i = 0; i < urlParts.length; i++) {
                        urlParts[i] = "http://" + urlParts[i].trim();
                    }

                    ShowInfo info = ShowInfo.builder()
                            .showPoster(e.selectFirst("poster").text())
                            .showName(e.selectFirst("prfnm").text())
                            .showExplain(e.selectFirst("sty").text())
                            .showCategory(e.selectFirst("genrenm").text())
                            .showAge(showAge)
                            .showDuration(e.selectFirst("prfruntime").text())
                            .showLocation(locationOpt.get())
                            .showStyUrl1(urlParts.length > 0 ? urlParts[0] : null)
                            .showStyUrl2(urlParts.length > 1 ? urlParts[1] : null)
                            .showStyUrl3(urlParts.length > 2 ? urlParts[2] : null)
                            .showStyUrl4(urlParts.length > 3 ? urlParts[3] : null)
                            .build();

                    infoRepository.save(info);
                }
            }
        }
    }

    // 관람연령에서 숫자 추출 함수
    private int extractAgeNumber(String ageText) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(ageText);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}
