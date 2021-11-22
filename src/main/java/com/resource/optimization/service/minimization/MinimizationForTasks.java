package com.resource.optimization.service.minimization;

import com.resource.optimization.entity.OptimizationItem;
import com.resource.optimization.entity.PhaseOptimizationItem;
import com.resource.optimization.entity.Project;
import com.resource.optimization.entity.ProjectOptimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MinimizationForTasks {

    Float inputMaxResourceOptimization;
    Integer numberOfStages;
    Float eps;

//    List<Float> minRangeValues = Arrays.asList(1f, 1f, 10f, 10f, 10f);
//
//    List<Float> sk = Arrays.asList(460f, 360f, 302f, 490f, 388f);

//    List<Float> minRangeValues = Arrays.asList(1f, 1f, 10f, 10f, 10f);

    //    List<Float> sk = Arrays.asList(460f, 360f, 302f, 490f, 388f);
    List<Float> sk; //= Arrays.asList(462.35f, 354.89f, 487.12f, 295.04f, 400.65f);

//    List<Float> maxRangeValues = sk;

    List<PhaseOptimizationItem> phaseOptimizationItems;
    List<List<Float>> optimizedResourcesValues;
    Project project;
    Integer maxNumberOfCalculations = 50_000;
    int numberOfCalculations;

    List<List<Float>> maxRangeValues;
    List<List<Float>> minRangeValues;

    public MinimizationForTasks(List<PhaseOptimizationItem> phaseOptimizationItems, Project project) {
        this.phaseOptimizationItems = phaseOptimizationItems;
        this.project = project;
        this.inputMaxResourceOptimization = project.getFinalCost();
        this.numberOfStages = phaseOptimizationItems.size();
        eps = 0.001f;

        optimizedResourcesValues = new ArrayList<>();
        minRangeValues = new ArrayList<>();
        maxRangeValues = new ArrayList<>();

        sk = phaseOptimizationItems.stream()
                .map(PhaseOptimizationItem::getValue)
                .collect(Collectors.toList());

        phaseOptimizationItems.forEach(phaseOptimizationItem ->
                minRangeValues.add(phaseOptimizationItem.getOptimizationItems().stream()
                        .map(optimizationItem -> optimizationItem.getTask()
                                .getMinimumImplementationCost())
                        .collect(Collectors.toList())));

        phaseOptimizationItems.forEach(phaseOptimizationItem ->
                maxRangeValues.add(phaseOptimizationItem.getOptimizationItems().stream()
                        .map(optimizationItem -> optimizationItem.getTask()
                                .getMaximumImplementationCost())
                        .collect(Collectors.toList())));

//        for (int k = 0; k < numberOfTasks; ++k) {
//            optimizedResourcesValues.add();
//        }

        for (int k = 0; k < phaseOptimizationItems.size(); ++k) {
            optimizedResourcesValues.add(roundListValues(generateValuesForOptimization(minRangeValues.get(k), maxRangeValues.get(k)), 2));
        }
//        optimizedResourcesValues.add(Arrays.asList(169.56f, 51.99f, 13.34f, 111.05f, 178.21f));
//        optimizedResourcesValues.add(Arrays.asList(130.63f, 62.07f, 0.0f, 16.46f, 164.14f));
//        optimizedResourcesValues.add(Arrays.asList(141.06f, 122.34f, 204.3f, 69.01f, 0.0f));
        System.out.println("Test");
    }


    public ProjectOptimization calculateMinimization() {
        optimizedResourcesValues.forEach(list -> {
            list.forEach(element -> System.out.print(element + "  "));
            System.out.println();
        });

        System.out.println("Sum of optimizedResourcesValues array " + optimizedResourcesValues.stream()
                .flatMap(List::stream)
                .reduce(0f, Float::sum));

        float initialOptimizationFunction = minimizationFunction(optimizedResourcesValues);
        System.out.println("initialOptimizationFunction " + initialOptimizationFunction);

        getMinimalOptimizationResult(initialOptimizationFunction);

        System.out.println("Result Array:");

        for (int k = 0; k < numberOfStages; ++k) {
            System.out.print("Task #" + (k + 1) + " ");
            roundListValues(optimizedResourcesValues.get(k), 2).forEach(element -> System.out.print(element + "  "));
            System.out.print("  Sum " + MinimizationUtils.round(optimizedResourcesValues.get(k).stream().reduce(0f, Float::sum), 2));

            System.out.println();
        }

        System.out.print("Sum  ");
        for (int k = 0; k < numberOfStages; ++k) {
            float sumForStage = 0f;
            for (int t = 0; t < optimizedResourcesValues.get(k).size(); ++t) {
                sumForStage += optimizedResourcesValues.get(k).get(t);
            }
            System.out.print(MinimizationUtils.round(sumForStage, 2) + "  ");
        }
        System.out.println();


        float sumOptimized = optimizedResourcesValues.stream()
                .flatMap(List::stream)
                .reduce(0f, Float::sum);

        System.out.println("Sum of optimizedValues array " + MinimizationUtils.round(sumOptimized, 2));

        System.out.println("minimizationFunction " + MinimizationUtils.round(minimizationFunction(optimizedResourcesValues), 2));

        return generateTasksOptimizationResults();
    }


    private ProjectOptimization generateTasksOptimizationResults() {
        ProjectOptimization projectOptimizationResult = new ProjectOptimization();

        for (int i = 0; i < phaseOptimizationItems.size(); ++i) {
            List<OptimizationItem> optimizationItems = phaseOptimizationItems.get(i).getOptimizationItems();

            for (int k = 0; k < optimizationItems.size(); ++k) {
                optimizationItems.get(k).setValue(MinimizationUtils.round(optimizedResourcesValues.get(i).get(k),2));
            }
        }

        projectOptimizationResult.setPhaseOptimizationItems(phaseOptimizationItems);
        projectOptimizationResult.setProject(project);

        return projectOptimizationResult;
    }

    private List<Float> roundListValues(List<Float> listToRoundValues, int decimalPlaces) {
        return listToRoundValues.stream()
                .map(value -> MinimizationUtils.round(value, decimalPlaces))
                .collect(Collectors.toList());
    }

    private Float getMinimalOptimizationResult(float minimalOptimization) {
        for (int i = 0; i < numberOfStages; ++i) {
            int stepSign = 1;
            float precision = 0.1f;
            float sumStageElementsOfOptimization;
            minimalOptimization = Math.abs(getSumOfTasksValues(i) - sk.get(i));

            do {
                float staticStep = 1;

                for (int k = 0; k < 5; ++k) {

                    List<Float> dynamicSteps = new ArrayList<>();
                    List<Float> optimizedValues = new ArrayList<>();

                    for (int r = 0; r < optimizedResourcesValues.get(i).size(); ++r) {
                        dynamicSteps.add(precision * optimizedResourcesValues.get(i).get(r));
                        optimizedValues.add(optimizedResourcesValues.get(i).get(r) + stepSign * staticStep * dynamicSteps.get(r));
                    }

                    for (int r = 0; r < optimizedResourcesValues.get(i).size(); ++r) {
                        if (Math.abs(optimizedValues.get(r)) < minRangeValues.get(i).get(r)) {
                            optimizedValues.set(r, minRangeValues.get(i).get(r));
                        }

                        if (Math.abs(optimizedValues.get(r)) > maxRangeValues.get(i).get(r)) {
                            optimizedValues.set(r, maxRangeValues.get(i).get(r));
                        }
                    }

                    float actualOptimization = Math.abs(optimizedValues.stream().reduce(0f, Float::sum) - sk.get(i));

                    if (actualOptimization < minimalOptimization) {
                        minimalOptimization = actualOptimization;

                        for (int r = 0; r < optimizedResourcesValues.get(i).size(); ++r) {
                            optimizedResourcesValues.get(i).set(r, optimizedValues.get(r));
                        }
                    }

                    staticStep *= 2;
                }

                sumStageElementsOfOptimization = getSumOfTasksValues(i);

                if (sumStageElementsOfOptimization > sk.get(i)) {
                    stepSign = -1;
                } else {
                    stepSign = 1;
                }

                precision *= 0.1f;

                if (precision == 0.0) {
                    precision = 0.01f;
                }
                numberOfCalculations += 1;
            } while (Math.abs(sumStageElementsOfOptimization - sk.get(i)) > eps
                    || numberOfCalculations > maxNumberOfCalculations);
        }

        return minimalOptimization;
    }

    private Float getSumOfTasksValues(int finalI) {
        return optimizedResourcesValues.get(finalI).stream()
                .reduce(0f, Float::sum);
    }

    private List<Float> generateValuesForOptimization(List<Float> minValues, List<Float> maxValues) {
        List<Float> resultList = new ArrayList<>();

        for (int i = 0; i < minValues.size(); ++i) {
            resultList.add((float) (minValues.get(i) + ((maxValues.get(i) - minValues.get(i)) / 2) * Math.random()));
        }

        return resultList;
    }

    private float minimizationFunction(List<List<Float>> optimizeList) {

        float sumOfMinimizationList = 0f;

        for (int k = 0; k < numberOfStages; k++) {
            float sumOptimizedTasksValues = optimizeList.get(k).stream().reduce(0f, Float::sum);
            sumOfMinimizationList += Math.abs(sumOptimizedTasksValues - sk.get(k));
        }

        return sumOfMinimizationList;
    }
}
