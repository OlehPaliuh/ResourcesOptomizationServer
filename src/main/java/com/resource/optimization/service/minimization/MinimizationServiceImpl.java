package com.resource.optimization.service.minimization;

import com.resource.optimization.entity.PhaseOptimizationItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinimizationServiceImpl implements MinimizationService {

    Float inputMaxResourceOptimization;
    Integer numberOfStages;
    Float eps;

    List<PhaseOptimizationItem> phaseOptimizationItems;
//    List<Task> tasks;
//    Project project;

    List<Float> optimizedValues;
    List<Float> tempOptimizedValues;

    public MinimizationServiceImpl(List<PhaseOptimizationItem> phaseOptimizationItems, Float finalCost) {
        this.phaseOptimizationItems = phaseOptimizationItems;
        this.inputMaxResourceOptimization = finalCost;
        this.numberOfStages = phaseOptimizationItems.size();
        this.eps = 0.0001f;
    }


    public List<PhaseOptimizationItem> calculateMinimization() {
        optimizedValues = roundListValues(generateValuesForOptimization(), 2);

        System.out.println("Initial Array:");
        optimizedValues.forEach(item -> System.out.println(item + "  "));

        tempOptimizedValues = new ArrayList<>(numberOfStages);

        for (int i = 0; i < numberOfStages; ++i) {
            tempOptimizedValues.add(optimizedValues.get(i));
        }

        float initialOptimizationFunction = minimizationFunction(optimizedValues);
        System.out.println("initialOptimizationFunction " + initialOptimizationFunction);
        Float minimalOptimization = initialOptimizationFunction;

        int stepSign = 1;
        float staticStep = 1f;
        float precision = 0.01f;

        do {
            minimalOptimization = getMinimalOptimizationResult(minimalOptimization, stepSign, staticStep, precision);
            saveToTemporaryList(optimizedValues);

            if (sumOptimizationListValues(tempOptimizedValues) > inputMaxResourceOptimization) {
                stepSign = -1;
            } else {
                stepSign = 1;
            }

            precision *= 0.1f;

            if (precision == 0.0) {
                precision = 0.1f;
            }
            System.out.println("precision " + precision);
        } while (minimalOptimization > 0.0001);

        System.out.println("Result Array:");

        roundListValues(optimizedValues, 2).forEach(element -> System.out.print(element + "  "));
        System.out.println("Sum of optimizedValues array " + MinimizationUtils.round(optimizedValues.stream().reduce(0f, Float::sum), 2));
        System.out.println("minimizationFunction " + MinimizationUtils.round(minimizationFunction(optimizedValues), 2));

        return generateTasksOptimizationResults();
    }


    private List<PhaseOptimizationItem> generateTasksOptimizationResults() {

        for (int k = 0; k < numberOfStages; ++k) {
            phaseOptimizationItems
                    .set(k, phaseOptimizationItems.get(k)
                            .setValue(MinimizationUtils.round(optimizedValues.get(k),2)));
        }

        return phaseOptimizationItems;
    }

    private List<Float> roundListValues(List<Float> listToRoundValues, int decimalPlaces) {
        return listToRoundValues.stream()
                .map(value -> MinimizationUtils.round(value, decimalPlaces))
                .collect(Collectors.toList());
    }

    private float sumOptimizationListValues(List<Float> list) {
        return list.stream().reduce(0f, Float::sum);
    }

    private Float getMinimalOptimizationResult(float minimalOptimization, int stepSign, float staticStep, float precision) {
        float actualOptimization = minimalOptimization;
        for (int k = 0; k < 5; ++k) {
            for (int i = 0; i < tempOptimizedValues.size(); ++i) {

                float dynamicStep = precision * tempOptimizedValues.get(i);

                float optimized = tempOptimizedValues.get(i) + stepSign * staticStep * dynamicStep;

                if (Math.abs(optimized) < phaseOptimizationItems.get(i).getMinimumCost()) {
                    optimized = phaseOptimizationItems.get(i).getMinimumCost();
                }

                if (Math.abs(optimized) > phaseOptimizationItems.get(i).getMaximumCost()) {
                    optimized = phaseOptimizationItems.get(i).getMaximumCost();
                }

                tempOptimizedValues.set(i, optimized);

                actualOptimization = minimizationFunction(tempOptimizedValues);

                if (actualOptimization < minimalOptimization) {
                    minimalOptimization = actualOptimization;

                    saveToOptimizedList(tempOptimizedValues);
                }
            }

            staticStep *= 2;
            System.out.println("staticStep " + staticStep);
        }

        return minimalOptimization;
    }

    private void saveToOptimizedList(List<Float> tempOptimizedList) {
        for (int i = 0; i < optimizedValues.size(); ++i) {
            optimizedValues.set(i, tempOptimizedList.get(i));
        }
    }

    private void saveToTemporaryList(List<Float> optimizedList) {
        for (int i = 0; i < optimizedList.size(); ++i) {
            tempOptimizedValues.set(i, optimizedList.get(i));
        }
    }

    private List<Float> generateValuesForOptimization() {
        List<Float> resultList = new ArrayList<>(numberOfStages);

        for (int i = 0; i < numberOfStages; ++i) {
            float minOfTask = phaseOptimizationItems.get(i).getMinimumCost();
            float maxOfTask = phaseOptimizationItems.get(i).getMaximumCost();
            resultList.add((float) (minOfTask + ((maxOfTask - minOfTask) / 2) * Math.random()));
        }

        return resultList;
    }

    private float minimizationFunction(List<Float> list) {

        list = roundListValues(list, 2);

        float sumOfMinimizationList = list.stream().reduce(0f, Float::sum);

        return (sumOfMinimizationList - inputMaxResourceOptimization) * (sumOfMinimizationList - inputMaxResourceOptimization);
    }
}
