package com.varol.WellPass_Mananagement_System.service.auth;

import io.jsonwebtoken.Claims;

public interface JwtService {
    
    String generateAccessToken(Long userId, String email, String role);
    
    String generateRefreshToken(Long userId);
    
    Claims validateToken(String token);
    
    Long getUserIdFromToken(String token);
    
    String getEmailFromToken(String token);
    
    String getRoleFromToken(String token);
    
    boolean isTokenExpired(String token);
}