package com.company.crm.repository;

import com.company.crm.domain.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    @Query("SELECT d FROM Delivery d WHERE " +
           "(:shippingCompanyName IS NULL OR LOWER(d.shippingCompanyName) LIKE LOWER(CONCAT('%', :shippingCompanyName, '%'))) AND " +
           "(:address IS NULL OR LOWER(d.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
           "(:headquarter IS NULL OR LOWER(d.headquarter) LIKE LOWER(CONCAT('%', :headquarter, '%')))")
    Page<Delivery> findByFilters(
            @Param("shippingCompanyName") String shippingCompanyName,
            @Param("address") String address,
            @Param("headquarter") String headquarter,
            Pageable pageable);
}