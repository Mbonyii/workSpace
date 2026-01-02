package com.varol.WellPass_Mananagement_System.service.auth;

import com.varol.WellPass_Mananagement_System.dtos.request.auth.LoginRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.auth.RefreshTokenRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.auth.RegisterRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.auth.AuthResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.auth.TokenResponse;

public interface AuthService {
    
    AuthResponse register(RegisterRequest request);
    
    AuthResponse login(LoginRequest request);
    
    TokenResponse refreshToken(RefreshTokenRequest request);
    
    void logout(Long userId);
    
    void validateToken(String token);
}
