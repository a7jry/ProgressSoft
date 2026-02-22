package com.progresssoft.fxwarehouse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "deals")
@Data
@Builder
@NoArgsConstructor   // ✅ Hibernate needs this
@AllArgsConstructor  // Optional, keeps your builder happy
public class Deal {

    @Id
    private String dealUniqueId;

    private String fromCurrency;
    private String toCurrency;
    private Instant dealTimestamp;
    private BigDecimal dealAmount;
}
