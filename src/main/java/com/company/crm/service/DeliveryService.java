package com.company.crm.service;

import com.company.crm.domain.model.Delivery;
import com.company.crm.dto.DeliveryFilterDto;
import com.company.crm.dto.DeliveryRequestDto;
import com.company.crm.dto.DeliveryResponseDto;
import com.company.crm.dto.PageResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryService {
    List<Delivery> findAll();
    
    PageResponseDto<DeliveryResponseDto> findAllWithPagination(Pageable pageable);
    
    PageResponseDto<DeliveryResponseDto> findByFilters(DeliveryFilterDto filterDto, Pageable pageable);
    
    Delivery save(Delivery delivery);
    
    DeliveryResponseDto create(DeliveryRequestDto requestDto);
    
    DeliveryResponseDto update(Long id, DeliveryRequestDto requestDto);
    
    Delivery findById(Long id);
    
    DeliveryResponseDto getById(Long id);
    
    void delete(Long id);
}