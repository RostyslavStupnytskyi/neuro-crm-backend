package com.company.crm.repository;

import com.company.crm.domain.model.Lead;
import com.company.crm.domain.model.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByStatus(LeadStatus status);
}