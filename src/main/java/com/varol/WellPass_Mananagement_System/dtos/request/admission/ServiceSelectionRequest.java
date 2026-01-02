package com.varol.WellPass_Mananagement_System.dtos.request.admission;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSelectionRequest {

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Service Provider ID is required")
    private Long serviceProviderId;
}






