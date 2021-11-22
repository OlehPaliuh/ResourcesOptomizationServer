package com.resource.optimization.service;

import com.resource.optimization.entity.OptimizationItem;
import com.resource.optimization.entity.PhaseOptimizationItem;
import com.resource.optimization.exception.ProjectNotFoundException;
import com.resource.optimization.repository.OptimizationItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptimizationItemService {

    private final OptimizationItemRepository optimizationItemRepository;

    public OptimizationItemService(OptimizationItemRepository optimizationItemRepository) {
        this.optimizationItemRepository = optimizationItemRepository;
    }

    public List<OptimizationItem> getAllOptimizationItems() {
        return optimizationItemRepository.findAll();
    }

    public OptimizationItem getOptimizationItemById(Long optimizationItemId) {
        return optimizationItemRepository.findById(optimizationItemId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Optimization Item with id %s not found", optimizationItemId)));
    }

    public OptimizationItem createOptimizationItem(OptimizationItem optimizationItem) {
        System.out.println("OptimizationItem: " + optimizationItem.toString());
        return optimizationItemRepository.save(optimizationItem);
    }

    public void deleteOptimizationItem(Long optimizationItemId) {
        OptimizationItem optimizationItem = getOptimizationItemById(optimizationItemId);
        optimizationItemRepository.delete(optimizationItem);
    }
}