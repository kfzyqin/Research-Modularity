package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenyueqin on 22/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsFast extends GRNFitnessFunctionMultipleTargets {

    protected final int perturbationCycleSize;
    protected List<DataGene[][]> perturbationPool;

    public GRNFitnessFunctionMultipleTargetsFast(int[][] targets, int maxCycle, int perturbations,
                                                 double perturbationRate, final int perturbationCycleSize) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFast(int[] target1, int[] target2, int maxCycle, int perturbations,
                                                 double perturbationRate, final int perturbationCycleSize) {
        super(target1, target2, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFast(int[] target1, int[] target2, int[] target3, int maxCycle,
                                                 int perturbations, double perturbationRate,
                                                 final int perturbationCycleSize) {
        super(target1, target2, target3, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFast(int[][] targets, int maxCycle, int perturbations,
                                                 double perturbationRate, List<Integer> thresholdOfAddingTarget,
                                                 final int perturbationCycleSize) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFast(int[] target1, int[] target2, int maxCycle, int perturbations,
                                                 double perturbationRate, List<Integer> thresholdOfAddingTarget,
                                                 final int perturbationCycleSize) {
        super(target1, target2, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFast(int[] target1, int[] target2, int[] target3, int maxCycle,
                                                 int perturbations, double perturbationRate,
                                                 List<Integer> thresholdOfAddingTarget,
                                                 final int perturbationCycleSize) {
        super(target1, target2, target3, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    protected void generatePerturbationPool() {
        perturbationPool = new ArrayList<>(targets.length);
        for (int[] target : targets) {
            perturbationPool.add(generateInitialAttractors(perturbationCycleSize, perturbationRate, target));
        }
    }

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype,
                                       @NotNull final int[] target,
                                       @NotNull final DataGene[][] perturbationTargets) {
        List<Integer> targetList = convertIntArrayToIntegerList(target);
        Map<SimpleMaterial, Double> phenotypeFitnessMap = (targetPhenotypeFitnessMap.containsKey(
                targetList) ? targetPhenotypeFitnessMap.get(targetList) : new HashMap<>());
        if (phenotypeFitnessMap.containsKey(phenotype)) {
            return phenotypeFitnessMap.get(phenotype);
        }
        double fitnessValue = 0;
        int perturbationIndex = 0;
        while (perturbationIndex < perturbations) {
            DataGene[] currentAttractor = perturbationTargets[perturbationIndex % perturbationCycleSize];
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype, target);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                int hammingDistance = this.getHammingDistance(currentAttractor, target);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) target.length))), 5);
                fitnessValue += thisFitness;
            } else {
                fitnessValue += 0;
            }
            perturbationIndex += 1;
        }

        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        phenotypeFitnessMap.put(phenotype, networkFitness);
        targetPhenotypeFitnessMap.put(targetList, phenotypeFitnessMap);
        return networkFitness;
    }

    @Override
    public double evaluate(SimpleMaterial phenotype, int generation) {
        List<Integer> currentTargetIndices = this.getCurrentTargetIndices(generation);
        double fitnessValue = 0;
        for (Integer targetIndex : currentTargetIndices) {
            int[] aTarget = this.targets[targetIndex];
            DataGene[][] perturbationTargets = this.perturbationPool.get(targetIndex);
            fitnessValue += this.evaluateOneTarget(phenotype, aTarget, perturbationTargets);
        }
        return fitnessValue / currentTargetIndices.size();
    }

    @Override
    public double evaluate(SimpleMaterial phenotype) {
        return evaluate(phenotype, 0);
    }

    @Override
    public void update() {

    }
}
