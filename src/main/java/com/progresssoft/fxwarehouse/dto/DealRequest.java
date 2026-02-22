package com.progresssoft.fxwarehouse.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class DealRequest {
    @NotBlank
    private String dealUniqueId;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    private String fromCurrency;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    private String toCurrency;

    @NotNull
    private Instant dealTimestamp;

    @NotNull
    @Positive
    private BigDecimal dealAmount;
}
