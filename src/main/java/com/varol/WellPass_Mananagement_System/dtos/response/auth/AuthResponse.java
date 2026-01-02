package com.varol.WellPass_Mananagement_System.dtos.response.auth;

import com.varol.WellPass_Mananagement_System.model.user.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType = "Bearer";

    private Long userId;

    private String email;

    private String fullName;

    private Role role;
}