package com.company.crm.service;

import com.company.crm.domain.model.AuditLog;
import com.company.crm.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityAuditService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAuditService.class);
    
    private final AuditLogRepository auditLogRepository;
    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public SecurityAuditService(AuditLogRepository auditLogRepository, 
                               HttpServletRequest request,
                               ObjectMapper objectMapper) {
        this.auditLogRepository = auditLogRepository;
        this.request = request;
        this.objectMapper = objectMapper;
    }
    
    public void auditLogin(String username, boolean success) {
        String action = success ? "LOGIN_SUCCESS" : "LOGIN_FAILURE";
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("remoteIp", getClientIP());
        data.put("userAgent", request.getHeader("User-Agent"));
        
        logSecurityEvent(action, "USER", username, data);
    }
    
    public void auditLogout(String username) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("remoteIp", getClientIP());
        
        logSecurityEvent("LOGOUT", "USER", username, data);
    }
    
    public void auditPasswordChange(String username) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("remoteIp", getClientIP());
        
        logSecurityEvent("PASSWORD_CHANGE", "USER", username, data);
    }
    
    public void auditRegistration(String username) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("remoteIp", getClientIP());
        data.put("userAgent", request.getHeader("User-Agent"));
        
        logSecurityEvent("USER_REGISTRATION", "USER", username, data);
    }
    
    public void auditAccessDenied(String resource) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "anonymous";
        
        Map<String, Object> data = new HashMap<>();
        data.put("resource", resource);
        data.put("remoteIp", getClientIP());
        
        logSecurityEvent("ACCESS_DENIED", "USER", username, data);
    }
    
    public void auditRoleChange(String username, String role, boolean added) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String actorUsername = auth != null ? auth.getName() : "system";
        
        Map<String, Object> data = new HashMap<>();
        data.put("targetUser", username);
        data.put("role", role);
        data.put("action", added ? "ADDED" : "REMOVED");
        data.put("actor", actorUsername);
        
        logSecurityEvent("ROLE_CHANGE", "USER", username, data);
    }
    
    private void logSecurityEvent(String action, String entityType, String entityId, Map<String, Object> data) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setAction(action);
            auditLog.setEntityType(entityType);
            auditLog.setEntityId(entityId);
            auditLog.setDelta(objectMapper.writeValueAsString(data));
            auditLog.setTimestamp(LocalDateTime.now());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            auditLog.setActorId(auth != null ? auth.getName() : "system");
            
            auditLogRepository.save(auditLog);
            
            // Also log to application logs for real-time monitoring
            logger.info("Security event: {} | Entity: {}:{} | Actor: {} | Data: {}", 
                    action, entityType, entityId, auditLog.getActorId(), data);
        } catch (Exception e) {
            logger.error("Failed to log security event", e);
        }
    }
    
    private String getClientIP() {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}