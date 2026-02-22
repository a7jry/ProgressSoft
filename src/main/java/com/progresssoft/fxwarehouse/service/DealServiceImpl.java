package com.progresssoft.fxwarehouse.service;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.entity.Deal;
import com.progresssoft.fxwarehouse.exception.DuplicateDealException;
import com.progresssoft.fxwarehouse.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    @Override
    public DealResponse saveDeal(DealRequest request) {
        log.info("Processing deal {}", request.getDealUniqueId());

        dealRepository.findByDealUniqueId(request.getDealUniqueId())
                .ifPresent(d -> { throw new DuplicateDealException("Deal already exists"); });

        Deal deal = Deal.builder()
                .dealUniqueId(request.getDealUniqueId())
                .fromCurrency(request.getFromCurrency())
                .toCurrency(request.getToCurrency())
                .dealTimestamp(request.getDealTimestamp())
                .dealAmount(request.getDealAmount())
                .build();

        dealRepository.save(deal);

        return DealResponse.builder()
                .dealUniqueId(deal.getDealUniqueId())
                .fromCurrency(deal.getFromCurrency())
                .toCurrency(deal.getToCurrency())
                .dealTimestamp(deal.getDealTimestamp())
                .dealAmount(deal.getDealAmount())
                .build();
    }
}
