package com.varol.WellPass_Mananagement_System.implementation.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.auth.LoginRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.auth.RefreshTokenRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.auth.RegisterRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.auth.AuthResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.auth.TokenResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.DuplicateResourceException;
import com.varol.WellPass_Mananagement_System.exception.custom.InvalidCredentialsException;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.user.User;
import com.varol.WellPass_Mananagement_System.repository.user.UserRepository;
import com.varol.WellPass_Mananagement_System.security.JwtUtil;
import com.varol.WellPass_Mananagement_System.service.auth.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(savedUser.getEmail())
                .password(savedUser.getPassword())
                .authorities("ROLE_" + savedUser.getRole().name())
                .build();
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("role", savedUser.getRole().name());
        
        String token = jwtUtil.generateToken(userDetails, claims);

        return buildAuthResponse(savedUser, token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("role", user.getRole().name());
            
            String token = jwtUtil.generateToken(userDetails, claims);

            log.info("User logged in successfully: {}", user.getEmail());
            return buildAuthResponse(user, token);
            
        } catch (Exception e) {
            log.error("Login failed for email: {}", request.getEmail(), e);
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        throw new UnsupportedOperationException("Refresh token not yet implemented");
    }

    @Override
    public void logout(Long userId) {
        log.info("User logout: {}", userId);
    }

    @Override
    public void validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            if (username == null) {
                throw new InvalidCredentialsException("Invalid token");
            }
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid or expired token");
        }
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        AuthResponse response = new AuthResponse();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole());
        return response;
    }
}