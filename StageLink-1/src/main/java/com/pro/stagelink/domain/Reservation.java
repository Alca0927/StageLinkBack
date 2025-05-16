package com.pro.stagelink.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBL_RESERVATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_NO")
    private int reservationNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER")
    private Member member; // 회원 정보 (Member Entity 필요)

    @ManyToOne
    @JoinColumn(name = "SHOW_NO")
    private Show show; // 공연 정보 (Show Entity 필요)

    @ManyToOne
    @JoinColumn(name = "SEAT_ID")
    private ShowSeat seat; // 좌석 정보 (ShowSeat Entity 필요)

    @Column(name = "RESERVATION_DATE")
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ReservationStatus status;
}