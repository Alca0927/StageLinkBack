package com.pro.stagelink.domain;

import lombok.*;

import java.time.LocalDateTime;

import com.pro.stagelink.entity.Member;

import jakarta.persistence.*;

@Entity
@Table(name = "TBL_REFUND")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFUND_NO")
    private int refundNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER")
    private Member member; // 회원 정보

    @ManyToOne
    @JoinColumn(name = "RESERVATION_NO")
    private Reservation reservation; // 예매 정보

    @ManyToOne
    @JoinColumn(name = "SEAT_NO")
    private ShowSeat seat; // 좌석 정보

    @Column(name = "REFUND_DATE")
    private LocalDateTime refundDate;
}
