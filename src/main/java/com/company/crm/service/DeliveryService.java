package com.company.crm.service;

import com.company.crm.domain.model.Delivery;
import java.util.List;

public interface DeliveryService {
    List<Delivery> findAll();
    Delivery save(Delivery delivery);
    Delivery findById(Long id);
    void delete(Long id);
}