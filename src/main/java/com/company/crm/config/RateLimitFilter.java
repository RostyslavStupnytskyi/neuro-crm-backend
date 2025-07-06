package com.company.crm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final Map<String, RequestCounter> REQUEST_COUNTERS = new ConcurrentHashMap<>();
    
    @Value("${app.security.rate-limit.limit:100}")
    private int rateLimit;
    
    @Value("${app.security.rate-limit.duration:3600}")
    private int rateLimitDuration;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip rate limiting for non-auth endpoints
        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String clientIp = getClientIP(request);
        
        if (isRateLimited(clientIp)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Please try again later.");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isRateLimited(String clientIp) {
        long currentTime = System.currentTimeMillis() / 1000;
        
        RequestCounter counter = REQUEST_COUNTERS.computeIfAbsent(clientIp, 
                k -> new RequestCounter(currentTime));
        
        // Reset counter if time window has passed
        if (currentTime - counter.getStartTime() > rateLimitDuration) {
            counter.reset(currentTime);
        }
        
        // Increment and check
        int requestCount = counter.incrementAndGet();
        return requestCount > rateLimit;
    }
    
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            // Get the first IP in case of multiple proxies
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
    
    private static class RequestCounter {
        private final AtomicInteger count = new AtomicInteger(0);
        private long startTime;
        
        public RequestCounter(long startTime) {
            this.startTime = startTime;
        }
        
        public int incrementAndGet() {
            return count.incrementAndGet();
        }
        
        public long getStartTime() {
            return startTime;
        }
        
        public void reset(long newStartTime) {
            count.set(0);
            this.startTime = newStartTime;
        }
    }
}