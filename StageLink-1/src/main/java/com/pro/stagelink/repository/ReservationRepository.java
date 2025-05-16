package com.pro.stagelink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.stagelink.domain.Reservation;



public interface ReservationRepository extends JpaRepository<Reservation, Integer>  {

}
