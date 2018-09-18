package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue extends GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob {

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, perturbationSizes, stride);
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(int[][] targets, int maxCycle, double perturbationRate, List<Integer> thresholdOfAddingTarget, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes, stride);
    }

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
                                       DataGene[][] startAttractors) {
        Map<Integer, List<Double>> perturbationPathDistanceMap = new HashMap<>();
        double fitnessAvgSum = 0;

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
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add(hammingDistance);
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(hammingDistance)));
                }
            } else {
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add((double) target.length);
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList((double) target.length)));
                }
            }
        }
        for (Integer key : perturbationPathDistanceMap.keySet()) {
            double avgDist = GeneralMethods.getAverageNumber(perturbationPathDistanceMap.get(key));
            double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(key);
            double contribution = (1 - (avgDist / ((double) target.length)));
            fitnessAvgSum += perturbedLengthWeight * Math.pow(contribution, 5);
        }
        double arithmeticMean = fitnessAvgSum / perturbationPathDistanceMap.size();
        return 1 - Math.pow(Math.E, (-3 * arithmeticMean));
    }
}
