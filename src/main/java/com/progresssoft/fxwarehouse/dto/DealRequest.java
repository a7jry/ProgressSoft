package com.progresssoft.fxwarehouse.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealRequest {

    @NotBlank(message = "Deal Unique ID is required")
    private String dealUniqueId;

    @NotBlank(message = "From Currency ISO Code is required")
    @Size(min = 3, max = 3, message = "ISO Code must be 3 characters")
    private String fromCurrency;

    @NotBlank(message = "To Currency ISO Code is required")
    @Size(min = 3, max = 3, message = "ISO Code must be 3 characters")
    private String toCurrency;

    @NotNull(message = "Timestamp is required")
    private Instant dealTimestamp;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal dealAmount;
}