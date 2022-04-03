package com.malexj.service;

import com.malexj.dto.LogResponseDto;
import com.malexj.dto.ModelEventDto;
import com.malexj.mapper.ModelEventEntityMapper;
import com.malexj.repository.ModelEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventLogsService {

    private final ModelEventRepository repository;
    private final ModelEventEntityMapper mapper;

    public LogResponseDto findAllLogs() {
        List<ModelEventDto> dtoList = repository.findTop20ByOrderByCreatedAsc().stream() //
                .map(mapper::evenEntityToDto) //
                .collect(Collectors.toList());
        return LogResponseDto.builder() //
                .modelEventList(dtoList) //
                .total(repository.count()) //
                .build();
    }

}