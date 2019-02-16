package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight extends GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue {

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight(int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, perturbationSizes, stride);
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight(int[][] targets, int maxCycle, double perturbationRate, List<Integer> thresholdOfAddingTarget, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes, stride);
    }

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
                                       DataGene[][] startAttractors) {
//        System.out.println("We are now evaluating one target. ");
//        System.out.println("Size of start attractors: " + startAttractors.length);
//        System.out.println("Whether this can reduce the duplications: " + GeneralMethods.getArrayDuplicateElementNo(startAttractors).size());
        HashMap<Integer, Integer> aDistribution = GeneralMethods.getPerturbationNumberDistribution(startAttractors, target);
//        System.out.println(aDistribution);

        double g = 0;

        Map<Integer, List<Double>> perturbationPathDistanceMap = new HashMap<>();

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
            List<Double> aPerturbationPathDistances = perturbationPathDistanceMap.get(key);
//            double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(key);
            double perturbedLengthWeight = 1;
            List<Double> gammas = new ArrayList<>();
            for (Double aPerturbationPathDistance : aPerturbationPathDistances) {
                double aD = (1 - (aPerturbationPathDistance / ((double) target.length)));
                double oneGamma = Math.pow(aD, 5);
                gammas.add(oneGamma);
            }
            double averageGamma = GeneralMethods.getAverageNumber(gammas);
            g += perturbedLengthWeight * averageGamma;
        }
        return 1 - Math.pow(Math.E, (-3 * g));
    }
}
