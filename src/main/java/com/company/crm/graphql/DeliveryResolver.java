package com.company.crm.graphql;

import com.company.crm.domain.model.Delivery;
import com.company.crm.service.DeliveryService;
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
    public Delivery delivery(@Argument Long id) {
        return service.findById(id);
    }

    @MutationMapping
    public Delivery createDelivery(@Argument("input") Delivery input) {
        return service.save(input);
    }

    @MutationMapping
    public Delivery updateDelivery(@Argument Long id, @Argument("input") Delivery input) {
        input.setId(id);
        return service.save(input);
    }

    @MutationMapping
    public Boolean deleteDelivery(@Argument Long id) {
        service.delete(id);
        return true;
    }
}