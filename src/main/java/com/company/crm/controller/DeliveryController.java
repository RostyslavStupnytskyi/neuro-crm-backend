package com.company.crm.controller;

import com.company.crm.domain.model.Delivery;
import com.company.crm.service.DeliveryService;
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
    public List<Delivery> list() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Delivery> create(@Validated @RequestBody Delivery delivery) {
        Delivery saved = service.save(delivery);
        return ResponseEntity.created(URI.create("/api/v1/deliveries/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> get(@PathVariable Long id) {
        Delivery delivery = service.findById(id);
        return delivery != null ? ResponseEntity.ok(delivery) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Delivery> update(@PathVariable Long id, @Validated @RequestBody Delivery delivery) {
        delivery.setId(id);
        Delivery updated = service.save(delivery);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}