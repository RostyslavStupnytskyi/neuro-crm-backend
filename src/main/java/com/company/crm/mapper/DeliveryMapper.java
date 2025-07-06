package com.company.crm.mapper;

import com.company.crm.domain.model.Delivery;
import com.company.crm.dto.DeliveryRequestDto;
import com.company.crm.dto.DeliveryResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryMapper {

    public Delivery toEntity(DeliveryRequestDto dto) {
        Delivery entity = new Delivery();
        entity.setShippingCompanyName(dto.getShippingCompanyName());
        entity.setAddress(dto.getAddress());
        entity.setHeadquarter(dto.getHeadquarter());
        return entity;
    }

    public void updateEntityFromDto(DeliveryRequestDto dto, Delivery entity) {
        entity.setShippingCompanyName(dto.getShippingCompanyName());
        entity.setAddress(dto.getAddress());
        entity.setHeadquarter(dto.getHeadquarter());
    }

    public DeliveryResponseDto toDto(Delivery entity) {
        DeliveryResponseDto dto = new DeliveryResponseDto();
        dto.setId(entity.getId());
        dto.setShippingCompanyName(entity.getShippingCompanyName());
        dto.setAddress(entity.getAddress());
        dto.setHeadquarter(entity.getHeadquarter());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<DeliveryResponseDto> toDtoList(List<Delivery> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}