package com.pro.stagelink.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tbl_showseat")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SEAT_ID")
    private int seatId;
	
	@Column(name = "SEAT_CLASS")
    private String seatClass; // R, S, A, VIP
	
	@Column(name = "SEAT_NUMBER")
    private int seatNumber;
	
	@Column(name = "SEAT_RESERVED")
    private int seatReserved = 0; // 0: 예약 안 됨

    @ManyToOne
    @JoinColumn(name = "show_no")
    private Show show;


	public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
	}


	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;		
	}


	public void setSeatReserved(int seatReserved) {
        this.seatReserved = seatReserved;		
	}


	public void setShow(Show show) {
        this.show = show;		
	}
}
