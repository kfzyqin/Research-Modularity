package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by zhenyueqin on 25/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsSeparateHidden extends GRNFitnessFunctionMultipleTargets {
    private final int unitHiddenTargetSize;
    private final int targetUnit;

    public GRNFitnessFunctionMultipleTargetsSeparateHidden(int[][] targets,
                                                           int maxCycle,
                                                           int perturbations,
                                                           double perturbationRate,
                                                           final int unitHiddenTargetSize,
                                                           final int targetUnit) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.unitHiddenTargetSize = unitHiddenTargetSize;
        this.targetUnit = targetUnit;
    }

    public GRNFitnessFunctionMultipleTargetsSeparateHidden(int[][] targets,
                                                           int maxCycle,
                                                           int perturbations,
                                                           double perturbationRate,
                                                           List<Integer> thresholdOfAddingTarget,
                                                           final int unitHiddenTargetSize,
                                                           final int targetUnit) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.unitHiddenTargetSize = unitHiddenTargetSize;
        this.targetUnit = targetUnit;
    }

    //todo: bugs here
    private DataGene[][] generateInitialHiddenAttractors(int setSize, double probability, int[] target) {
        /*
        Generate a random set of perturbations of the targets set for evaluation.
         */
        DataGene[][] returnables = new DataGene[setSize][target.length + (target.length / targetUnit) * unitHiddenTargetSize];
        for (int i=0; i<setSize; i++) {
            int currentGeneIndex = 0;
            int targetGeneIndex = 0;
            int counter = 0;

            while (targetGeneIndex < target.length) {
//                System.out.println("target gene index: " + targetGeneIndex);
                if (counter % this.targetUnit != 0 || counter == 0) {
                    returnables[i][currentGeneIndex] = new DataGene(target[targetGeneIndex]);
                    if (Math.random() < probability) {
                        returnables[i][currentGeneIndex].flip();
                    }
                    currentGeneIndex += 1;
                    targetGeneIndex += 1;
                } else {
                    for (int k=0; k<this.unitHiddenTargetSize; k++) {
//                        System.out.println("index: " + currentGeneIndex);
                        returnables[i][currentGeneIndex] = new DataGene(1);
                        currentGeneIndex += 1;
                    }
                    returnables[i][currentGeneIndex] = new DataGene(target[targetGeneIndex]);
                    currentGeneIndex += 1;
                    targetGeneIndex += 1;
                }
                counter += 1;
            }

        int endingIndex = target.length + (target.length / targetUnit) * unitHiddenTargetSize - 1;
        for (int k=endingIndex; k>(endingIndex - this.unitHiddenTargetSize); k--) {
//                System.out.println("k: " + k);
            returnables[i][k] = new DataGene(1);
        }

//            System.out.println("tarr: " + Arrays.toString(target));
//            System.out.println("here: " + Arrays.toString(returnables[i]));
    }
        return returnables;
}

    protected boolean hasNotAttainedAttractor(final DataGene[] currentState, final DataGene[] updatedState) {
        int differenceCounts = 0;
        for (int i=0; i<currentState.length; i++) {
            if (currentState[i].getValue() != updatedState[i].getValue()) {
                differenceCounts += 1;
            }
        }
        return differenceCounts != 0;
    }

    protected double evaluateOneTargetWithHidden(@NotNull final SimpleMaterial phenotype,
                                                 @NotNull int[] target) {
        DataGene[][] startAttractors = this.generateInitialHiddenAttractors(perturbations, perturbationRate, target);
        double fitnessValue = 0;

        for (DataGene[] startAttractor : startAttractors) {
            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                int hammingDistance = this.getHammingDistanceWithHiddenTargets(currentAttractor, target);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) target.length))), 5);
                fitnessValue += thisFitness;
            } else {
                fitnessValue += 0;
            }
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
            fitnessValue += this.evaluateOneTargetWithHidden(phenotype, aTarget);
        }

        return fitnessValue / currentTargetIndices.size();
    }

    @Override
    public double evaluate(SimpleMaterial phenotype) {
        return evaluate(phenotype, 0);
    }

    public int getHammingDistanceWithHiddenTargets(DataGene[] attractor, int[] target) {
//        System.out.println("attractor: " + Arrays.toString(attractor));
//        System.out.println("target: " + Arrays.toString(target));
        int matches = 0;

        int currentGeneIndex = 0;
        int targetGeneIndex = 0;
        int counter = 0;

        while (targetGeneIndex < target.length) {
            if (counter % this.targetUnit != 0 || counter == 0) {

//                System.out.println("====");
//                System.out.println("target gene index: " + targetGeneIndex);
//                System.out.println("curre gene target: " + currentGeneIndex);

                if (attractor[currentGeneIndex].getValue() == target[targetGeneIndex]) {
                    matches += 1;
                }

                currentGeneIndex += 1;
                targetGeneIndex += 1;
            } else {
                for (int k=0; k<this.unitHiddenTargetSize; k++) {
//                    if (attractor[currentGeneIndex].getValue() == target[targetGeneIndex]) {
//                        matches += 1;
//                    }
                    currentGeneIndex += 1;
                }

//                System.out.println("target gene index: " + targetGeneIndex);
//                System.out.println("curre gene target: " + currentGeneIndex);

                if (attractor[currentGeneIndex].getValue() == target[targetGeneIndex]) {
                    matches += 1;
                }

                currentGeneIndex += 1;
                targetGeneIndex += 1;
            }
            counter += 1;
        }
        return target.length - matches;
    }
}
