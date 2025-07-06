package com.company.crm.dto;

public class DeliveryFilterDto {
    
    private String shippingCompanyName;
    private String address;
    private String headquarter;
    
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
}