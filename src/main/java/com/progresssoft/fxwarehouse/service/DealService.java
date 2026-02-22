package com.progresssoft.fxwarehouse.service;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;

public interface DealService {
    DealResponse saveDeal(DealRequest request);
}
