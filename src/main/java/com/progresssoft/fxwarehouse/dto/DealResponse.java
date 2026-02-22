package com.progresssoft.fxwarehouse.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class DealResponse {

    private String dealUniqueId;
    private String fromCurrency;
    private String toCurrency;
    private Instant dealTimestamp;
    private BigDecimal dealAmount;

    // Public constructor
    public DealResponse(String dealUniqueId, String fromCurrency, String toCurrency,
                        Instant dealTimestamp, BigDecimal dealAmount) {
        this.dealUniqueId = dealUniqueId;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount = dealAmount;
    }

    // Public getters
    public String getDealUniqueId() { return dealUniqueId; }
    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency() { return toCurrency; }
    public Instant getDealTimestamp() { return dealTimestamp; }
    public BigDecimal getDealAmount() { return dealAmount; }
}
