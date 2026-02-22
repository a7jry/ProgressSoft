package com.progresssoft.fxwarehouse.repository;

import com.progresssoft.fxwarehouse.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, String> {
    Optional<Deal> findByDealUniqueId(String dealUniqueId);
}
