package com.pro.stagelink.domain;

public enum ReservationStatus {
	TEMP,       // 임시 예약 (결제 전)
    CONFIRMED,  // 예약 완료 (결제 완료)
    CANCELED    // 예약 취소
}
