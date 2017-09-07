package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zhenyueqin on 25/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsHidden extends GRNFitnessFunctionMultipleTargets {
    private final int hiddenTargetSize;

    public GRNFitnessFunctionMultipleTargetsHidden(int[][] targets,
                                                   int maxCycle,
                                                   int perturbations,
                                                   double perturbationRate,
                                                   final int hiddenTargetSize) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.hiddenTargetSize = hiddenTargetSize;
    }

    public GRNFitnessFunctionMultipleTargetsHidden(int[][] targets,
                                                   int maxCycle,
                                                   int perturbations,
                                                   double perturbationRate,
                                                   List<Integer> thresholdOfAddingTarget,
                                                   final int hiddenTargetSize) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.hiddenTargetSize = hiddenTargetSize;
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
                int hammingDistance = this.getHammingDistanceWithHiddenTargets(currentAttractor, target, hiddenTargetSize);
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

    public int getHammingDistanceWithHiddenTargets(DataGene[] attractor, int[] target, int hiddenTargetSize) {
        int count = 0;
        for (int i=0; i<attractor.length - hiddenTargetSize; i++) {
            if (attractor[i].getValue() == target[i]) {
                count += 1;
            }
        }
        return attractor.length - hiddenTargetSize - count;
    }
}
