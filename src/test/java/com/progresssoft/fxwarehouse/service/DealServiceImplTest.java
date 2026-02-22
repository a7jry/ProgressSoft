package com.progresssoft.fxwarehouse.service;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.entity.Deal;
import com.progresssoft.fxwarehouse.exception.DuplicateDealException;
import com.progresssoft.fxwarehouse.repository.DealRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @DisplayName("Should successfully save a valid deal")
    void saveDeal_Success() {
        // Arrange
        DealRequest request = new DealRequest("D1", "USD", "JOD", Instant.now(), new BigDecimal("100.00"));
        Deal mockEntity = new Deal("D1", "USD", "JOD", request.getDealTimestamp(), request.getDealAmount());
        
        when(dealRepository.existsById("D1")).thenReturn(false);
        when(dealRepository.save(any(Deal.class))).thenReturn(mockEntity);

        // Act
        DealResponse response = dealService.saveDeal(request);

        // Assert
        assertNotNull(response);
        assertEquals("D1", response.getDealUniqueId());
        assertEquals("USD", response.getFromCurrency());
        verify(dealRepository, times(1)).save(any(Deal.class));
    }

    @Test
    @DisplayName("Should throw DuplicateDealException when ID already exists")
    void saveDeal_ThrowsDuplicateException() {
        // Arrange
        DealRequest request = new DealRequest("D1", "USD", "JOD", Instant.now(), new BigDecimal("100.00"));
        when(dealRepository.existsById("D1")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateDealException.class, () -> dealService.saveDeal(request));
        verify(dealRepository, never()).save(any(Deal.class));
    }
    
    @Test
    @DisplayName("Should correctly map all fields from Request to Entity")
    void saveDeal_VerifyMappingDataCorrectly() {
        // Arrange
        DealRequest request = new DealRequest("D-XYZ", "EUR", "GBP", Instant.now(), new BigDecimal("50.25"));
        when(dealRepository.existsById(anyString())).thenReturn(false);
        when(dealRepository.save(any(Deal.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        DealResponse response = dealService.saveDeal(request);

        // Assert
        assertEquals(request.getDealUniqueId(), response.getDealUniqueId());
        assertEquals(request.getDealAmount(), response.getDealAmount());
        assertEquals(request.getFromCurrency(), response.getFromCurrency());
        assertEquals(request.getToCurrency(), response.getToCurrency());
    }

    @Test
    @DisplayName("Should handle extremely large deal amounts correctly")
    void saveDeal_LargeAmount_SavesCorrectly() {
        // Arrange
        BigDecimal hugeAmount = new BigDecimal("999999999999.99");
        DealRequest request = new DealRequest("HUGE-1", "USD", "JOD", Instant.now(), hugeAmount);
        
        when(dealRepository.existsById("HUGE-1")).thenReturn(false);
        when(dealRepository.save(any(Deal.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        DealResponse response = dealService.saveDeal(request);
        
        // Assert
        assertEquals(hugeAmount, response.getDealAmount());
    }

    @Test
    @DisplayName("Should fail if the database operation fails")
    void saveDeal_DatabaseFailure_ThrowsException() {
        // Arrange
        DealRequest request = new DealRequest("D1", "USD", "JOD", Instant.now(), new BigDecimal("100.00"));
        when(dealRepository.existsById("D1")).thenReturn(false);
        when(dealRepository.save(any(Deal.class))).thenThrow(new RuntimeException("DB Connection Lost"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> dealService.saveDeal(request));
    }
}