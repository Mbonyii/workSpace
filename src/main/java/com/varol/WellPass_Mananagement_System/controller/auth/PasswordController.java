package com.varol.WellPass_Mananagement_System.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.request.auth.ResetPasswordRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordController {

    @PostMapping("/forgot")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody String email) {
        return ResponseEntity.ok(ApiResponse.success("Password reset link sent to email", null));
    }

    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {
        
        return ResponseEntity.ok(ApiResponse.success("Password reset successfully", null));
    }
}