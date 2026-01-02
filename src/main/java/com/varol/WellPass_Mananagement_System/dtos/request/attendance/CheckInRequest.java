package com.varol.WellPass_Mananagement_System.dtos.request.attendance;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Service Provider ID is required")
    private Long serviceProviderId;
}







