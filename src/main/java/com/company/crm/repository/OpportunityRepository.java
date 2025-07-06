package com.company.crm.repository;

import com.company.crm.domain.model.Account;
import com.company.crm.domain.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findByAccount(Account account);
    List<Opportunity> findByStage(String stage);
    List<Opportunity> findByCloseDateBetween(LocalDate start, LocalDate end);
}