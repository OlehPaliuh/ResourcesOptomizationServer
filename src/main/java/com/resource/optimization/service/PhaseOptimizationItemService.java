package com.resource.optimization.service;

import com.resource.optimization.entity.PhaseOptimizationItem;
import com.resource.optimization.entity.ProjectOptimization;
import com.resource.optimization.exception.ProjectNotFoundException;
import com.resource.optimization.repository.PhaseOptimizationItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseOptimizationItemService {

    private final PhaseOptimizationItemRepository phaseOptimizationItemRepository;

    public PhaseOptimizationItemService(PhaseOptimizationItemRepository phaseOptimizationItemRepository) {
        this.phaseOptimizationItemRepository = phaseOptimizationItemRepository;
    }

    public List<PhaseOptimizationItem> getAllPhaseOptimizationItems() {
        return phaseOptimizationItemRepository.findAll();
    }

    public PhaseOptimizationItem getPhaseOptimizationItemById(Long phaseOptimizationItemId) {
        return phaseOptimizationItemRepository.findById(phaseOptimizationItemId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Phase Optimization Item with id %s not found", phaseOptimizationItemId)));
    }

    public PhaseOptimizationItem createPhaseOptimizationItem(PhaseOptimizationItem phaseOptimizationItem) {
        System.out.println("PhaseOptimizationItem: " + phaseOptimizationItem.toString());
        return phaseOptimizationItemRepository.save(phaseOptimizationItem);
    }

    public void deletePhaseOptimizationItem(Long phaseOptimizationItemId) {
        PhaseOptimizationItem phaseOptimizationItem = getPhaseOptimizationItemById(phaseOptimizationItemId);
        phaseOptimizationItemRepository.delete(phaseOptimizationItem);
    }
}