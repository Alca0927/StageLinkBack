package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TBL_SHOWINFO")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOW_INFO")
    private int showInfo;

    @Column(name = "SHOW_POSTER")
    private String showPoster;

    @Column(name = "SHOW_NAME")
    private String showName;

    @Column(name = "SHOW_EXPLAIN")
    private String showExplain;

    @Column(name = "SHOW_CATEGORY")
    private String showCategory;

    @Column(name = "SHOW_AGE")
    private int showAge;

    @Column(name = "SHOW_DURATION")
    private String showDuration;

    // 공연 장소 (외래 키)
    @ManyToOne
    @JoinColumn(name = "showlocation_id")
    private ShowLocation showLocation;

    @Column(name = "SHOW_STYURL1")
    private String showStyUrl1;

    @Column(name = "SHOW_STYURL2")
    private String showStyUrl2;

    @Column(name = "SHOW_STYURL3")
    private String showStyUrl3;

    @Column(name = "SHOW_STYURL4")
    private String showStyUrl4;
    

    public void changeShowPoster(String showPoster) {
		 this.showPoster = showPoster;
	}
    
    public void changeShowName(String showName) {
		 this.showName = showName;
	}
    
    public void changeShowExplain(String showExplain) {
		 this.showExplain = showExplain;
	}
    
    public void changeShowCategory(String showCategory) {
		 this.showCategory = showCategory;
	}
    
    public void changeShowAge(int showAge) {
		 this.showAge = showAge;
	}
    
    public void changeShowDuration(String showDuration) {
		 this.showDuration = showDuration;
	}
    
    public void changeShowLocation(ShowLocation showLocation) {
		 this.showLocation = showLocation;
	}
    
    public void changeShowStyUrl1(String showStyUrl1) {
		 this.showStyUrl1 = showStyUrl1;
	}
    
    public void changeShowStyUrl2(String showStyUrl2) {
		 this.showStyUrl2 = showStyUrl2;
	}
    
    public void changeShowStyUrl3(String showStyUrl3) {
		 this.showStyUrl3 = showStyUrl3;
	}
    
    public void changeShowStyUrl4(String showStyUrl4) {
		 this.showStyUrl4 = showStyUrl4;
	}
}
