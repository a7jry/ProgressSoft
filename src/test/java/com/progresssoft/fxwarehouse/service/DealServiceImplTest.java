package com.progresssoft.fxwarehouse.service;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse; // Add this import
import com.progresssoft.fxwarehouse.entity.Deal;
import com.progresssoft.fxwarehouse.exception.DuplicateDealException;
import com.progresssoft.fxwarehouse.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealServiceImpl dealService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDeal_NewDeal_SavesSuccessfully() {
        DealRequest request = new DealRequest("D101", "USD", "EUR", Instant.now(), BigDecimal.valueOf(1000));

        // The Mock should still return an Entity (Deal), because the Repository works with Entities
        Deal mockSavedEntity = new Deal("D101", "USD", "EUR", request.getDealTimestamp(), request.getDealAmount());
        
        when(dealRepository.existsById("D101")).thenReturn(false);
        when(dealRepository.save(any(Deal.class))).thenReturn(mockSavedEntity);

        // FIX: Change 'Deal' to 'DealResponse' here
        DealResponse result = dealService.saveDeal(request);

        assertNotNull(result);
        assertEquals("D101", result.getDealUniqueId());
        assertEquals("USD", result.getFromCurrency());
        verify(dealRepository, times(1)).save(any(Deal.class));
    }

    @Test
    void saveDeal_DuplicateDeal_ThrowsException() {
        DealRequest request = new DealRequest("D101", "USD", "EUR", Instant.now(), BigDecimal.valueOf(1000));

        when(dealRepository.existsById("D101")).thenReturn(true);

        assertThrows(DuplicateDealException.class, () -> dealService.saveDeal(request));

        verify(dealRepository, never()).save(any(Deal.class));
    }
}