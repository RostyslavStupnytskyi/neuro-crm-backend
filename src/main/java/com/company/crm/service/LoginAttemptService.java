package com.company.crm.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final LoadingCache<String, Integer> attemptsCache;
    
    @Value("${app.security.login.max-attempts:5}")
    private int maxAttempts;
    
    @Value("${app.security.login.lock-duration-minutes:30}")
    private int lockDurationMinutes;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(lockDurationMinutes, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= maxAttempts;
        } catch (ExecutionException e) {
            return false;
        }
    }
    
    public int getAttemptsLeft(String key) {
        try {
            int attempts = attemptsCache.get(key);
            return Math.max(0, maxAttempts - attempts);
        } catch (ExecutionException e) {
            return maxAttempts;
        }
    }
}