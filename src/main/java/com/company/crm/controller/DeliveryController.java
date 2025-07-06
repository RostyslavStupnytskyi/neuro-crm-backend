package com.company.crm.controller;

import com.company.crm.domain.model.Delivery;
import com.company.crm.dto.DeliveryFilterDto;
import com.company.crm.dto.DeliveryRequestDto;
import com.company.crm.dto.DeliveryResponseDto;
import com.company.crm.dto.PageResponseDto;
import com.company.crm.service.DeliveryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<PageResponseDto<DeliveryResponseDto>> listPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        return ResponseEntity.ok(service.findAllWithPagination(pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<PageResponseDto<DeliveryResponseDto>> filter(
            @ModelAttribute DeliveryFilterDto filterDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        return ResponseEntity.ok(service.findByFilters(filterDto, pageable));
    }

    @PostMapping
    public ResponseEntity<DeliveryResponseDto> create(@Validated @RequestBody DeliveryRequestDto requestDto) {
        DeliveryResponseDto responseDto = service.create(requestDto);
        return ResponseEntity.created(URI.create("/api/v1/deliveries/" + responseDto.getId())).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDto> get(@PathVariable Long id) {
        try {
            DeliveryResponseDto responseDto = service.getById(id);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponseDto> update(@PathVariable Long id, @Validated @RequestBody DeliveryRequestDto requestDto) {
        try {
            DeliveryResponseDto responseDto = service.update(id, requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}