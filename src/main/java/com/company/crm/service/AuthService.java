package com.company.crm.service;

import com.company.crm.service.dto.AuthRequest;
import com.company.crm.service.dto.AuthResponse;
import com.company.crm.service.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(RegisterRequest request);
}