package com.company.crm.graphql;

import com.company.crm.domain.model.Delivery;
import com.company.crm.dto.DeliveryFilterDto;
import com.company.crm.dto.DeliveryRequestDto;
import com.company.crm.dto.DeliveryResponseDto;
import com.company.crm.dto.PageResponseDto;
import com.company.crm.service.DeliveryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DeliveryResolver {

    private final DeliveryService service;

    public DeliveryResolver(DeliveryService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Delivery> deliveries() {
        return service.findAll();
    }

    @QueryMapping
    public PageResponseDto<DeliveryResponseDto> deliveriesPaged(
            @Argument Integer page,
            @Argument Integer size,
            @Argument String sortBy,
            @Argument String sortDir) {
        
        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        String field = sortBy != null ? sortBy : "id";
        Sort.Direction direction = sortDir != null && sortDir.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(direction, field));
        return service.findAllWithPagination(pageable);
    }

    @QueryMapping
    public PageResponseDto<DeliveryResponseDto> deliveriesFiltered(
            @Argument DeliveryFilterDto filter,
            @Argument Integer page,
            @Argument Integer size,
            @Argument String sortBy,
            @Argument String sortDir) {
        
        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        String field = sortBy != null ? sortBy : "id";
        Sort.Direction direction = sortDir != null && sortDir.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(direction, field));
        return service.findByFilters(filter, pageable);
    }

    @QueryMapping
    public Delivery delivery(@Argument Long id) {
        return service.findById(id);
    }

    @QueryMapping
    public DeliveryResponseDto deliveryDto(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public DeliveryResponseDto createDelivery(@Argument("input") DeliveryRequestDto input) {
        return service.create(input);
    }

    @MutationMapping
    public DeliveryResponseDto updateDelivery(@Argument Long id, @Argument("input") DeliveryRequestDto input) {
        return service.update(id, input);
    }

    @MutationMapping
    public Boolean deleteDelivery(@Argument Long id) {
        service.delete(id);
        return true;
    }
}