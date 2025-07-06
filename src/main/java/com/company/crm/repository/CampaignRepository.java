package com.company.crm.repository;

import com.company.crm.domain.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByChannel(String channel);
    List<Campaign> findByStartDateAfter(LocalDate date);
    List<Campaign> findByEndDateBefore(LocalDate date);
    List<Campaign> findByTargetSegment(String targetSegment);
}