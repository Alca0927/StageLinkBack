package com.pro.stagelink.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TBL_SHOWLOCATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SHOW_LOCATION")
	private int showLocation;

    @Column(name = "FACILITY_ID", unique = true)
    private String facilityId; // mt10id
	
	@Column(name = "LOCATION_NAME")
	private String locationName;
	
	@Column(name = "LOCATION_ADDRESS")
	private String locationAddress;
	
	public void changeFacilityId(String facilityId) {
	        this.facilityId = facilityId;
	}
	 
	 
	public void changeLocation_name(String locationName) {
		 this.locationName = locationName;
	 }
	
	public void changeLocation_address(String locationAddress) {
		 this.locationAddress = locationAddress;
	 }
}
