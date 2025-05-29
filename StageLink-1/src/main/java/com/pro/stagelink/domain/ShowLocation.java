package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TBL_SHOWLOCATION")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "showlocation_id")
	private int showlocation;

    @Column(name = "FACILITY_ID", unique = true)
    private String facilityId; // mt10id
	
	@Column(name = "LOCATION_NAME")
	private String locationName;
	
	@Column(name = "LOCATION_ADDRESS")
	private String locationAddress;
	
	 
	public void changeLocationName(String locationName) {
		 this.locationName = locationName;
	 }
	
	public void changeLocationAddress(String locationAddress) {
		 this.locationAddress = locationAddress;
	 }
}
