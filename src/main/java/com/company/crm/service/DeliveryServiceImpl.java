package com.company.crm.service;

import com.company.crm.domain.model.Delivery;
import com.company.crm.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryServiceImpl(DeliveryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Delivery> findAll() {
        return repository.findAll();
    }

    @Override
    public Delivery save(Delivery delivery) {
        return repository.save(delivery);
    }

    @Override
    public Delivery findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}