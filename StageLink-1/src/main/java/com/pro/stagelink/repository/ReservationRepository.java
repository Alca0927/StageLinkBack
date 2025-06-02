package com.pro.stagelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pro.stagelink.domain.Reservation;



public interface ReservationRepository extends JpaRepository<Reservation, Integer>  {
	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.status = 'CONFIRMED'")
	int countReservation();
	
	@Query("SELECT COUNT(r) FROM Reservation r WHERE r.status = 'CANCELED'")
	int countRefund();
}
