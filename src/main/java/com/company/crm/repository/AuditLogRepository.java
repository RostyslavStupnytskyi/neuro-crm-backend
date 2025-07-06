package com.company.crm.repository;

import com.company.crm.domain.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId);
    List<AuditLog> findByActionAndTimestampBetween(String action, LocalDateTime start, LocalDateTime end);
    List<AuditLog> findByActorId(String actorId);
}