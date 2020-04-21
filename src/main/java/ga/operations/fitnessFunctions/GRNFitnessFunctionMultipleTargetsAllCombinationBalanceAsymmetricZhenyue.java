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

    public boolean printCyclePath = false;
    public List<Integer> cycleDists = new ArrayList<>();

    protected ArrayList<DataGene[]> cyclePath = new ArrayList<>();

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
                                       DataGene[][] startAttractors) {
//        System.out.println("We are now evaluating one target. ");
//        System.out.println("Size of start attractors: " + startAttractors.length);
//        System.out.println("Whether this can reduce the duplications: " + GeneralMethods.getArrayDuplicateElementNo(startAttractors).size());
        HashMap<Integer, Integer> aDistribution = GeneralMethods.getPerturbationNumberDistribution(startAttractors, target);
//        System.out.println(aDistribution);

        double g = 0;

        Map<Integer, List<Double>> perturbationPathDistanceMap = new HashMap<>();

        Map<String, Double> perturbationCache = new HashMap<>();

        for (DataGene[] startAttractor : startAttractors) {
            int perturbedLength = (int) GeneralMethods.getOriginalHammingDistance(startAttractor, target);
            boolean cached = false;

            if (perturbationCache.containsKey(Arrays.deepToString(startAttractor))) {
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add(perturbationCache.get(Arrays.deepToString(startAttractor)));
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(perturbationCache.get(Arrays.deepToString(startAttractor)))));
                }
                cached = true;
                continue;
            }

            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            cyclePath.add(startAttractor.clone());

//            System.out.println(perturbationCache);

            List<DataGene[]> cycleStates = new ArrayList<>();

            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
                cyclePath.add(updatedState.clone());

                if (perturbationCache.containsKey(Arrays.deepToString(currentAttractor))) {
//                    System.out.println("caught cached! ");
                    if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                        perturbationPathDistanceMap.get(perturbedLength).add(perturbationCache.get(Arrays.deepToString(currentAttractor)));
                    } else {
                        perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(perturbationCache.get(Arrays.deepToString(currentAttractor)))));
                    }
                    cached = true;
                    break;
                }

                cycleStates.add(updatedState);

            } while (currentRound < this.maxCycle && isNotStable);

            if (cached) {
                continue;
            }

            if (currentRound < maxCycle && !(cached)) {

                double hammingDistance = this.getHammingDistance(currentAttractor, target);
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add(hammingDistance);
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(hammingDistance)));
                }
                for (DataGene[] e : cycleStates) {
                    perturbationCache.put(Arrays.deepToString(e), hammingDistance);
                }

            } else {
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add((double) target.length);
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList((double) target.length)));
                }
                for (DataGene[] e : cycleStates) {
                    perturbationCache.put(Arrays.deepToString(e), (double) target.length);
                }
            }

            cyclePath.remove(cyclePath.size()-1);

            if (this.printCyclePath) {
                System.out.println("currentRound: \t" + currentRound);
                System.out.println("s attractor: \t" + Arrays.toString(startAttractor));
                System.out.println("target: \t\t" + Arrays.toString(target));
                System.out.println("initial distance: \t" + this.getHammingDistance(startAttractor, target));
                System.out.println("final distance: \t" + this.getHammingDistance(currentAttractor, target));
                List<Double> cycleDists = new ArrayList<>();
                for (DataGene[] aCyclePath : cyclePath) {
                    cycleDists.add(this.getHammingDistance(aCyclePath, target));
                }
                System.out.println(Arrays.deepToString(cyclePath.toArray()));
                System.out.println(cycleDists);
                System.out.println();
            }
            cycleDists.add(currentRound);

            cyclePath = new ArrayList<>();

        }
//        if (target.length <= 5) {
//            System.out.println("target: " + Arrays.toString(target));
//            System.out.println("GRN: " + phenotype);
//            System.out.println(perturbationPathDistanceMap);
//        }

        for (Integer aKey : perturbationPathDistanceMap.keySet()) {
            List<Double> aDistances = perturbationPathDistanceMap.get(aKey);
            Map<Double, Integer> aDistanceMap = new HashMap<>();
            for (Double aDist : aDistances) {
                if (aDistanceMap.containsKey(aDist)) {
                    aDistanceMap.put(aDist, aDistanceMap.get(aDist) + 1);
                } else {
                    aDistanceMap.put(aDist, 1);
                }
            }
//            System.out.println("Perturbation Size: " + aKey + "; Distance Map: " + aDistanceMap);

//            Set<Double> aSet = new HashSet<Double>(perturbationPathDistanceMap.get(aKey));
//            perturbationPathDistanceMap.get(aKey);
//            System.out.println("A Key: " + aKey + " Average Distance: " + aSet);
        }
        for (Integer key : perturbationPathDistanceMap.keySet()) {
            List<Double> aPerturbationPathDistances = perturbationPathDistanceMap.get(key);
            double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(key);
//            double perturbedLengthWeight = 1;
            List<Double> gammas = new ArrayList<>();
            for (Double aPerturbationPathDistance : aPerturbationPathDistances) {
                double aD = (1 - (aPerturbationPathDistance / ((double) target.length)));
                double oneGamma = Math.pow(aD, 5);
                gammas.add(oneGamma);
            }
            double averageGamma = GeneralMethods.getAverageNumber(gammas);
//            System.out.println("average Gamma: " + averageGamma);
            g += perturbedLengthWeight * averageGamma;
        }
//        System.out.println("g: " + g);
        return 1 - Math.pow(Math.E, (-3 * g));
    }
}
