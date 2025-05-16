package com.pro.stagelink.service;

import com.pro.stagelink.dto.NoticeDTO;
import com.pro.stagelink.mapper.NoticeMapper;
import com.pro.stagelink.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final NoticeRepository repository;

    public NoticeService(NoticeRepository repository) {
        this.repository = repository;
    }

    public List<NoticeDTO> getAll() {
        return repository.findAll().stream()
                .map(NoticeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public long getCount() {
        return repository.countNotices();
    }

    public void save(NoticeDTO dto) {
        repository.save(NoticeMapper.toEntity(dto));
    }
}