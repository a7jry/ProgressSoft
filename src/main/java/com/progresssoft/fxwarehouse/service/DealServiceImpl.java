package com.progresssoft.fxwarehouse.service;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.entity.Deal;
import com.progresssoft.fxwarehouse.exception.DuplicateDealException;
import com.progresssoft.fxwarehouse.repository.DealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public DealResponse saveDeal(DealRequest request) {
        log.info("Received request to save deal: {}", request.getDealUniqueId());

        if (dealRepository.existsById(request.getDealUniqueId())) {
            log.error("Duplicate deal detected: {}", request.getDealUniqueId());
            throw new DuplicateDealException("Deal with ID " + request.getDealUniqueId() + " already exists.");
        }

        Deal deal = Deal.builder()
                .dealUniqueId(request.getDealUniqueId())
                .fromCurrency(request.getFromCurrency())
                .toCurrency(request.getToCurrency())
                .dealTimestamp(request.getDealTimestamp())
                .dealAmount(request.getDealAmount())
                .build();

        Deal saved = dealRepository.save(deal);
        log.info("Deal {} saved successfully to database.", saved.getDealUniqueId());

        return new DealResponse(
                saved.getDealUniqueId(),
                saved.getFromCurrency(),
                saved.getToCurrency(),
                saved.getDealTimestamp(),
                saved.getDealAmount()
        );
    }
}