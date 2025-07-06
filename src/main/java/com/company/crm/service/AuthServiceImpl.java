package com.company.crm.service;

import com.company.crm.domain.model.Role;
import com.company.crm.domain.model.User;
import com.company.crm.repository.RoleRepository;
import com.company.crm.repository.UserRepository;
import com.company.crm.service.dto.AuthRequest;
import com.company.crm.service.dto.AuthResponse;
import com.company.crm.service.dto.RegisterRequest;
import com.company.crm.service.exception.AccountLockedException;
import com.company.crm.service.exception.ResourceAlreadyExistsException;
import com.company.crm.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider,
                          LoginAttemptService loginAttemptService, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.loginAttemptService = loginAttemptService;
        this.request = request;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        String ip = getClientIP();
        String username = request.getUsername();
        
        // Check if the IP or username is blocked
        if (loginAttemptService.isBlocked(ip) || loginAttemptService.isBlocked(username)) {
            logger.warn("Blocked login attempt from IP: {} for username: {}", ip, username);
            throw new AccountLockedException("Account is locked due to too many failed attempts. Please try again later.");
        }
        
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                loginAttemptService.loginFailed(ip);
                loginAttemptService.loginFailed(username);
                
                int attemptsLeft = loginAttemptService.getAttemptsLeft(username);
                logger.warn("Failed login attempt for user: {}, attempts left: {}", username, attemptsLeft);
                
                throw new BadCredentialsException("Invalid password. Attempts left: " + attemptsLeft);
            }

            // Login successful, reset counters
            loginAttemptService.loginSucceeded(ip);
            loginAttemptService.loginSucceeded(username);
            
            List<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());

            String token = tokenProvider.generateToken(user.getUsername(), roles);
            String refreshToken = tokenProvider.generateRefreshToken(user.getUsername());
            
            logger.info("User logged in successfully: {}", username);
            
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", token);
            tokens.put("refreshToken", refreshToken);
            
            return new AuthResponse(token, user.getUsername(), roles);
        } catch (ResourceNotFoundException e) {
            // Don't reveal that the username doesn't exist
            loginAttemptService.loginFailed(ip);
            logger.warn("Login attempt for non-existent user: {}", username);
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already in use");
        }

        // Validate password strength
        validatePasswordStrength(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Assign default USER role
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        userRepository.save(user);
        
        logger.info("New user registered: {}", request.getUsername());

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = tokenProvider.generateToken(user.getUsername(), roles);
        String refreshToken = tokenProvider.generateRefreshToken(user.getUsername());
        
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", token);
        tokens.put("refreshToken", refreshToken);
        
        return new AuthResponse(token, user.getUsername(), roles);
    }
    
    private void validatePasswordStrength(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
        
        if (!(hasUppercase && hasLowercase && hasDigit && hasSpecialChar)) {
            throw new IllegalArgumentException(
                "Password must contain at least one uppercase letter, one lowercase letter, " +
                "one digit, and one special character"
            );
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