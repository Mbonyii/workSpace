package com.varol.WellPass_Mananagement_System.dtos.response.auth;

import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.model.user.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private Role role;

    private Boolean isActive;

    private Boolean isEmailVerified;

    private Boolean isPhoneVerified;

    private LocalDateTime createdAt;
}