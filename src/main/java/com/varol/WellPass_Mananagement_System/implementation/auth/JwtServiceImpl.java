package com.varol.WellPass_Mananagement_System.implementation.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.varol.WellPass_Mananagement_System.security.JwtUtil;
import com.varol.WellPass_Mananagement_System.service.auth.JwtService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtil jwtUtil;

    @Override
    public String generateAccessToken(Long userId, String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        
        UserDetails userDetails = User.builder()
                .username(email)
                .password("")
                .authorities(new SimpleGrantedAuthority("ROLE_" + role))
                .build();
        
        return jwtUtil.generateToken(userDetails, claims);
    }

    @Override
    public String generateRefreshToken(Long userId) {
        return null;
    }

    @Override
    public Claims validateToken(String token) {
        return jwtUtil.extractAllClaims(token);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("userId", Long.class);
    }

    @Override
    public String getEmailFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    @Override
    public String getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("role", String.class);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return jwtUtil.extractExpiration(token).before(new java.util.Date());
    }
}