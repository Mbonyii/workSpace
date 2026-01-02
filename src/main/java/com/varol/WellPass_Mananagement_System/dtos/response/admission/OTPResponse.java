package com.varol.WellPass_Mananagement_System.dtos.response.admission;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPResponse {

    private Boolean otpSent;

    private String message;

    private LocalDateTime expiresAt;

    private Integer attemptsRemaining;
}
