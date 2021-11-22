package com.resource.optimization.repository;

import com.resource.optimization.entity.OptimizationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptimizationItemRepository extends JpaRepository<OptimizationItem, Long> {

}
