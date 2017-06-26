package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.GeneRegulatoryNetworkHiddenTargets;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zhenyueqin on 25/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsFastHidden extends GRNFitnessFunctionMultipleTargets {
    private final int hiddenTargetSize;
    private final int perturbationCycleSize;
    private List<DataGene[][]> perturbationPool;

    public GRNFitnessFunctionMultipleTargetsFastHidden(int[][] targets,
                                                       int maxCycle,
                                                       int perturbations,
                                                       double perturbationRate,
                                                       final int perturbationCycleSize,
                                                       final int hiddenTargetSize) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        this.hiddenTargetSize = hiddenTargetSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFastHidden(int[] target1,
                                                       int[] target2,
                                                       int maxCycle,
                                                       int perturbations,
                                                       double perturbationRate,
                                                       final int perturbationCycleSize,
                                                       final int hiddenTargetSize) {
        super(target1, target2, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        this.hiddenTargetSize = hiddenTargetSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFastHidden(int[] target1,
                                                       int[] target2,
                                                       int[] target3,
                                                       int maxCycle,
                                                       int perturbations,
                                                       double perturbationRate,
                                                       final int perturbationCycleSize,
                                                       final int hiddenTargetSize) {
        super(target1, target2, target3, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        this.hiddenTargetSize = hiddenTargetSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFastHidden(int[][] targets,
                                                       int maxCycle,
                                                       int perturbations,
                                                       double perturbationRate,
                                                       List<Integer> thresholdOfAddingTarget,
                                                       final int perturbationCycleSize,
                                                       final int hiddenTargetSize) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        this.hiddenTargetSize = hiddenTargetSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFastHidden(int[] target1,
                                                       int[] target2,
                                                       int maxCycle,
                                                       int perturbations,
                                                       double perturbationRate,
                                                       List<Integer> thresholdOfAddingTarget,
                                                       final int perturbationCycleSize,
                                                       final int hiddenTargetSize) {
        super(target1, target2, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        this.hiddenTargetSize = hiddenTargetSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionMultipleTargetsFastHidden(int[] target1,
                                                       int[] target2,
                                                       int[] target3,
                                                       int maxCycle,
                                                       int perturbations,
                                                       double perturbationRate,
                                                       List<Integer> thresholdOfAddingTarget,
                                                       final int perturbationCycleSize,
                                                       final int hiddenTargetSize) {
        super(target1, target2, target3, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        this.hiddenTargetSize = hiddenTargetSize;
        generatePerturbationPool();
    }

    protected void generatePerturbationPool() {
        perturbationPool = new ArrayList<>(targets.length);
        for (int[] target : targets) {
            perturbationPool.add(generateInitialHiddenAttractors(perturbationCycleSize, perturbationRate, target));
        }
    }

    private DataGene[][] generateInitialHiddenAttractors(int setSize, double probability, int[] target) {
        /*
        Generate a random set of perturbations of the targets set for evaluation.
         */
        DataGene[][] returnables = new DataGene[setSize][target.length + hiddenTargetSize];
        for (int i=0; i<setSize; i++) {
            for (int j = 0; j<target.length + hiddenTargetSize; j++) {
                if (j < target.length) {
                    returnables[i][j] = new DataGene(target[j]);
                } else {
                    returnables[i][j] = new DataGene(1);
                }
                if (Math.random() < probability) {
                    returnables[i][j].flip();
                }
            }
        }
        return returnables;

//        DataGene[][] returnables = new DataGene[setSize][target.length];
//        for (int i=0; i<setSize; i++) {
//            for (int j = 0; j<target.length; j++) {
//                returnables[i][j] = new DataGene(target[j]);
//                if (Math.random() < probability) {
//                    returnables[i][j].flip();
//                }
//            }
//        }
//        return returnables;
    }

    protected double evaluateOneTargetWithHidden(@NotNull final SimpleMaterial phenotype,
                                       @NotNull int[] target,
                                       @NotNull final DataGene[][] perturbationTargets) {
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
                int hammingDistance = this.getHammingDistanceWithHiddenTargets(currentAttractor, target, hiddenTargetSize);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) target.length))), 5);
                fitnessValue += thisFitness;
            } else {
                fitnessValue += 0;
            }
            perturbationIndex += 1;
        }

        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return networkFitness;
    }

    @Override
    public double evaluate(SimpleMaterial phenotype, int generation) {
        List<Integer> currentTargetIndices = this.getCurrentTargetIndices(generation);
        double fitnessValue = 0;
        for (Integer targetIndex : currentTargetIndices) {
            int[] aTarget = this.targets[targetIndex];
            DataGene[][] perturbationTargets = this.perturbationPool.get(targetIndex);
            fitnessValue += this.evaluateOneTargetWithHidden(phenotype, aTarget, perturbationTargets);
        }
        return fitnessValue / currentTargetIndices.size();
    }

    @Override
    public double evaluate(SimpleMaterial phenotype) {
        return evaluate(phenotype, 0);
    }

    private int getHammingDistanceWithHiddenTargets(DataGene[] attractor, int[] target, int hiddenTargetSize) {
        int count = 0;
        for (int i=0; i<attractor.length - hiddenTargetSize; i++) {
            if (attractor[i].getValue() == target[i]) {
                count += 1;
            }
        }
        return attractor.length - hiddenTargetSize - count;
    }
}
