package com.pro.stagelink.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pro.stagelink.domain.Refund;
import com.pro.stagelink.domain.Reservation;
import com.pro.stagelink.dto.PageRequestDTO;
import com.pro.stagelink.dto.PageResponseDTO;
import com.pro.stagelink.dto.RefundDTO;
import com.pro.stagelink.dto.ReservationDTO;
import com.pro.stagelink.repository.RefundRepository;
import com.pro.stagelink.repository.ReservationRepository;
import com.pro.stagelink.repository.ShowInfoRepository;
import com.pro.stagelink.repository.ShowLocationRepository;
import com.pro.stagelink.repository.ShowRepository;
import com.pro.stagelink.repository.ShowSeatRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
	private final ModelMapper modelMapper;
	private final ReservationRepository reservertionRepository;
	private final RefundRepository refundRepository;
	
	// 조회
	@Override
	public ReservationDTO getReservation(int reservationNo) {
		Optional<Reservation> result = reservertionRepository.findById(reservationNo);
		Reservation reservation = result.orElseThrow();
		ReservationDTO dto = modelMapper.map(reservation, ReservationDTO.class);
		return dto;
	}

	// 목록
	@Override
	public PageResponseDTO<ReservationDTO> reservationList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("reservationNo").descending());

		Page<Reservation> result = reservertionRepository.findAll(pageable);

		List<ReservationDTO> dtoList = result.getContent().stream().map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<ReservationDTO> responseDTO = PageResponseDTO.<ReservationDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}

	// 환불 조회
	@Override
	public RefundDTO getRefund(int refundNo) {
		Optional<Refund> result = refundRepository.findById(refundNo);
		Refund refund = result.orElseThrow();
		RefundDTO dto = modelMapper.map(refund, RefundDTO.class);
		return dto;
	}

	// 환불 목록
	@Override
	public PageResponseDTO<RefundDTO> refundList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(), Sort.by("refundNo").descending());

		Page<Refund> result = refundRepository.findAll(pageable);

		List<RefundDTO> dtoList = result.getContent().stream().map(reservation -> modelMapper.map(reservation, RefundDTO.class))
				.collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		PageResponseDTO<RefundDTO> responseDTO = PageResponseDTO.<RefundDTO>withAll().dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

		return responseDTO;
	}

	@Override
	public int getReservationTotalCount() {
		return reservertionRepository.countReservation();
	}

	@Override
	public int getRefundTotalCount() {
		return reservertionRepository.countRefund();
	}
}
