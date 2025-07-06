package com.company.crm.service;

import com.company.crm.domain.model.Delivery;
import com.company.crm.dto.DeliveryFilterDto;
import com.company.crm.dto.DeliveryRequestDto;
import com.company.crm.dto.DeliveryResponseDto;
import com.company.crm.dto.PageResponseDto;
import com.company.crm.mapper.DeliveryMapper;
import com.company.crm.repository.DeliveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository repository;
    private final DeliveryMapper mapper;

    public DeliveryServiceImpl(DeliveryRepository repository, DeliveryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Delivery> findAll() {
        return repository.findAll();
    }

    @Override
    public PageResponseDto<DeliveryResponseDto> findAllWithPagination(Pageable pageable) {
        Page<Delivery> page = repository.findAll(pageable);
        List<DeliveryResponseDto> content = mapper.toDtoList(page.getContent());
        
        return new PageResponseDto<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

    @Override
    public PageResponseDto<DeliveryResponseDto> findByFilters(DeliveryFilterDto filterDto, Pageable pageable) {
        Page<Delivery> page = repository.findByFilters(
                filterDto.getShippingCompanyName(),
                filterDto.getAddress(),
                filterDto.getHeadquarter(),
                pageable
        );
        
        List<DeliveryResponseDto> content = mapper.toDtoList(page.getContent());
        
        return new PageResponseDto<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

    @Override
    public Delivery save(Delivery delivery) {
        return repository.save(delivery);
    }

    @Override
    @Transactional
    public DeliveryResponseDto create(DeliveryRequestDto requestDto) {
        Delivery delivery = mapper.toEntity(requestDto);
        Delivery savedDelivery = repository.save(delivery);
        return mapper.toDto(savedDelivery);
    }

    @Override
    @Transactional
    public DeliveryResponseDto update(Long id, DeliveryRequestDto requestDto) {
        Delivery delivery = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Delivery not found with id: " + id));
        
        mapper.updateEntityFromDto(requestDto, delivery);
        Delivery updatedDelivery = repository.save(delivery);
        return mapper.toDto(updatedDelivery);
    }

    @Override
    public Delivery findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DeliveryResponseDto getById(Long id) {
        Delivery delivery = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Delivery not found with id: " + id));
        return mapper.toDto(delivery);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}