package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob extends GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetric {
    protected final List<Double> perturbationLengthBinomialDistribution;

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob(int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, perturbationSizes, stride);
        perturbationLengthBinomialDistribution = GeneralMethods.getBinomialDistribution(targets[0].length, perturbationRate);
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob(int[][] targets, int maxCycle, double perturbationRate, List<Integer> thresholdOfAddingTarget, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes, stride);
        perturbationLengthBinomialDistribution = GeneralMethods.getBinomialDistribution(targets[0].length, perturbationRate);
    }

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
                                       DataGene[][] startAttractors) {
        double fitnessValue = 0;
        for (DataGene[] startAttractor : startAttractors) {
            int perturbedLength = (int) GeneralMethods.getOriginalHammingDistance(startAttractor, target);

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
                double hammingDistance = this.getHammingDistance(currentAttractor, target);
                double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(perturbedLength);
                double contribution = (1 - (hammingDistance / ((double) target.length)));
                double thisFitness = Math.pow(contribution, 5);
                fitnessValue += perturbedLengthWeight * thisFitness;
            } else {
                fitnessValue += 0;
            }
        }
        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return networkFitness;
    }
}
