package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class GRNFitnessFunctionWithMultipleTargets  implements FitnessFunctionWithMultipleTargets<SimpleMaterial> {
    private final int[][] targets;
    private final int maxCycle;
    private final int perturbations;
    private final double perturbationRate;
    private final List<Integer> thresholdOfAddingTarget;

    public GRNFitnessFunctionWithMultipleTargets(final int[][] targets, final int maxCycle, int perturbations, double perturbationRate) {
        this.targets = targets;
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        thresholdOfAddingTarget = new ArrayList<>(1);
        this.thresholdOfAddingTarget.add(0);
        this.perturbationRate = perturbationRate;
    }

    public GRNFitnessFunctionWithMultipleTargets(final int[] target1, final int[] target2,
                                                 final int maxCycle, int perturbations, double perturbationRate) {
        this.targets = new int[2][target1.length];
        this.targets[0] = target1;
        this.targets[1] = target2;
        filterTargets();
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        thresholdOfAddingTarget = new ArrayList<>(1);
        this.thresholdOfAddingTarget.add(0);
        this.perturbationRate = perturbationRate;
    }

    public GRNFitnessFunctionWithMultipleTargets(final int[] target1, final int[] target2, final int[] target3,
                                                 final int maxCycle, int perturbations, double perturbationRate) {
        this.targets = new int[3][target1.length];
        this.targets[0] = target1;
        this.targets[1] = target2;
        this.targets[2] = target3;
        filterTargets();
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        thresholdOfAddingTarget = new ArrayList<>(1);
        this.thresholdOfAddingTarget.add(0);
        this.perturbationRate = perturbationRate;
    }

    public GRNFitnessFunctionWithMultipleTargets(final int[][] targets, final int maxCycle, int perturbations,
                                                 double perturbationRate,
                                                 final List<Integer> thresholdOfAddingTarget) {
        this.targets = targets;
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        this.perturbationRate = perturbationRate;
        this.thresholdOfAddingTarget = thresholdOfAddingTarget;
        Collections.sort(this.thresholdOfAddingTarget);
        filterThresholds();
    }

    public GRNFitnessFunctionWithMultipleTargets(final int[] target1, final int[] target2,
                                                 final int maxCycle, int perturbations, double perturbationRate,
                                                 final List<Integer> thresholdOfAddingTarget) {
        this.targets = new int[2][target1.length];
        this.targets[0] = target1;
        this.targets[1] = target2;
        filterTargets();
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        this.perturbationRate = perturbationRate;
        this.thresholdOfAddingTarget = thresholdOfAddingTarget;
        Collections.sort(this.thresholdOfAddingTarget);
        filterThresholds();
    }

    public GRNFitnessFunctionWithMultipleTargets(final int[] target1, final int[] target2, final int[] target3,
                                                 final int maxCycle, int perturbations, double perturbationRate,
                                                 final List<Integer> thresholdOfAddingTarget) {
        this.targets = new int[3][target1.length];
        this.targets[0] = target1;
        this.targets[1] = target2;
        this.targets[2] = target3;
        filterTargets();
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        this.perturbationRate = perturbationRate;
        this.thresholdOfAddingTarget = thresholdOfAddingTarget;
        Collections.sort(this.thresholdOfAddingTarget);
        filterThresholds();
    }

    /**
     * Check whether lengths of targets are the same.
     */
    private void filterTargets() {
        if (targets.length == 1) return;
        int lastTargetLength = targets[0].length;
        for (int i = 1; i< targets.length; i++) {
            if (targets[i].length != lastTargetLength) {
                throw new IllegalArgumentException("Lengths of targets are different.");
            }
            lastTargetLength = targets[i].length;
        }
    }

    /**
     * The first threshold must be 1.
     */
    private void filterThresholds() {
        if (thresholdOfAddingTarget.get(0) != 0) {
            throw new IllegalArgumentException("The first threshold must be 1.");
        }
    }

    private boolean hasNotAttainedAttractor(final DataGene[] currentState, final DataGene[] updatedState) {
        int differenceCounts = 0;
        for (int i=0; i<currentState.length; i++) {
            if (currentState[i].getValue() != updatedState[i].getValue()) {
                differenceCounts += 1;
            }
        }
        return differenceCounts != 0;
    }

    private int checkActivationOrRepression(double influence) {
        if (influence > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    private DataGene[] updateState(DataGene[] currentState, SimpleMaterial phenotype, final int[] target) {
        DataGene[] updatedState = new DataGene[currentState.length];
        updatedState = this.initializeDataGeneArray(updatedState);
        for (int i=0; i<currentState.length; i++) {
            double influence = 0;
            for (int j=0; j<currentState.length; j++) {
                influence += (int) (phenotype.getGene(i*target.length + j)).getValue() * currentState[j].getValue();
            }
            updatedState[i].setValue(this.checkActivationOrRepression(influence));
        }
        return updatedState;
    }

    private DataGene[] initializeDataGeneArray(DataGene[] dataGenes) {
        DataGene[] emptyDataGeneArray = dataGenes.clone();
        for (int i=0; i<emptyDataGeneArray.length; i++) {
            emptyDataGeneArray[i] = new DataGene();
        }
        return emptyDataGeneArray;
    }

    private DataGene[][] generateInitialAttractors(int setSize, double probability, int[] target) {
        /*
        Generate a random set of perturbations of the targets set for evaluation.
         */
        DataGene[][] returnables = new DataGene[setSize][target.length];
        for (int i=0; i<setSize; i++) {
            for (int j = 0; j<target.length; j++) {
                returnables[i][j] = new DataGene(target[j]);
                if (Math.random() < probability) {
                    returnables[i][j].flip();
                }
            }
        }
        return returnables;
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        return evaluate(phenotype, 0);
    }

    @Override
    public void update() {

    }

    private int getHammingDistance(DataGene[] attractor, int[] target) {
        int count = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() == target[i]) {
                count += 1;
            }
        }
        return attractor.length - count;
    }

    private List<Integer> getCurrentTargetIndices(int generation) {
        List<Integer> currentTargetIndices = new ArrayList<>();
        for (int i=0; i<thresholdOfAddingTarget.size(); i++) {
            if (generation >= thresholdOfAddingTarget.get(i)) {
                currentTargetIndices.add(i);
            } else {
                break;
            }
        }
        return currentTargetIndices;
    }

    private double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target) {
        DataGene[][] startAttractors = this.generateInitialAttractors(perturbations, perturbationRate, target);
        double fitnessValue = 0;
        for (DataGene[] startAttractor : startAttractors) {
            DataGene[] currentAttractor = startAttractor;
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
        }
        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return fitnessValue;

//        int ones = 0;
//        for (int i=0; i<phenotype.getSize(); i++) {
//            int aPosition = (Integer) phenotype.getGene(i).getValue();
//            if (aPosition == 1) {
//                ones += 1;
//            }
//        }
//        return ones;
    }

    @Override
    public double evaluate(final SimpleMaterial phenotype, final int generation) {
        List<Integer> currentTargetIndices = this.getCurrentTargetIndices(generation);
        double fitnessValue = 0;
        for (Integer targetIndex : currentTargetIndices) {
            int[] aTarget = this.targets[targetIndex];
            fitnessValue += this.evaluateOneTarget(phenotype, aTarget);
        }
        return fitnessValue / currentTargetIndices.size();
    }
}
