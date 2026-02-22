package com.progresssoft.fxwarehouse.controller;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.service.DealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DealControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DealService dealService;

    @InjectMocks
    private DealController dealController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dealController).build();
    }

    @Test
    void createDeal_ReturnsSuccess() throws Exception {
        DealRequest request = new DealRequest("D101", "USD", "EUR", Instant.now(), BigDecimal.valueOf(1000));
        DealResponse response = new DealResponse("D101", "USD", "EUR", request.getDealTimestamp(), request.getDealAmount());

        when(dealService.saveDeal(any(DealRequest.class))).thenReturn(response);

        String jsonRequest = """
                {
                    "dealUniqueId": "D101",
                    "fromCurrency": "USD",
                    "toCurrency": "EUR",
                    "dealTimestamp": "%s",
                    "dealAmount": 1000
                }
                """.formatted(request.getDealTimestamp());

        mockMvc.perform(post("/api/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dealUniqueId").value("D101"))
                .andExpect(jsonPath("$.fromCurrency").value("USD"))
                .andExpect(jsonPath("$.toCurrency").value("EUR"))
                .andExpect(jsonPath("$.dealAmount").value(1000));
    }
}
