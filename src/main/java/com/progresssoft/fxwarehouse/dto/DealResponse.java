package com.progresssoft.fxwarehouse.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class DealResponse {
    private String dealUniqueId;
    private String fromCurrency;
    private String toCurrency;
    private Instant dealTimestamp;
    private BigDecimal dealAmount;
}
