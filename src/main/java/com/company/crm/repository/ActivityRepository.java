package com.company.crm.repository;

import com.company.crm.domain.model.Activity;
import com.company.crm.domain.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByType(ActivityType type);
    List<Activity> findByOwnerId(Long ownerId);
    List<Activity> findByDueDateBefore(LocalDateTime dateTime);
}