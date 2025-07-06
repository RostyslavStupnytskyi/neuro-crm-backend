package com.company.crm.dto;

import java.time.LocalDateTime;

public class DeliveryResponseDto {
    
    private Long id;
    private String shippingCompanyName;
    private String address;
    private String headquarter;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getShippingCompanyName() {
        return shippingCompanyName;
    }
    
    public void setShippingCompanyName(String shippingCompanyName) {
        this.shippingCompanyName = shippingCompanyName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getHeadquarter() {
        return headquarter;
    }
    
    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}