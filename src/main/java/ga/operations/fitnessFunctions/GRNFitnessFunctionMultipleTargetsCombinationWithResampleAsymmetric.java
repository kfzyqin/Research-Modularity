package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GRNFitnessFunctionMultipleTargetsCombinationWithResampleAsymmetric extends GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob {

    public GRNFitnessFunctionMultipleTargetsCombinationWithResampleAsymmetric(int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, perturbationSizes, stride);
    }

    public GRNFitnessFunctionMultipleTargetsCombinationWithResampleAsymmetric(int[][] targets, int maxCycle, double perturbationRate, List<Integer> thresholdOfAddingTarget, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes, stride);
    }

    public boolean printCyclePath = false;
    public List<Integer> cycleDists = new ArrayList<>();

    protected ArrayList<DataGene[]> cyclePath = new ArrayList<>();

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
                                       DataGene[][] startAttractors) {

        double g = 0;
        double weightSum = 0;
        Map<Integer, List<Double>> perturbationPathDistanceMap = new HashMap<>();

        Map<Integer, List<Double>> perturbationPathDistanceHighProbMap = new HashMap<>();

        List<Integer> samplingPerturbationLength = new ArrayList<>();

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


        for (int i = 0; i < target.length; i++) {
            double perturbedWeight = perturbationLengthBinomialDistribution.get(i);
            if (weightSum < 0.99) {
                weightSum += perturbedWeight;
            }
            else {
                samplingPerturbationLength.add(i);
            }
        }
//        System.out.println(samplingPerturbationLength);

        for (int i = 0; i < target.length; i++) {
            if (!samplingPerturbationLength.contains(i)) {
                perturbationPathDistanceHighProbMap.put(i, perturbationPathDistanceMap.get(i));
            } else {
                perturbationPathDistanceHighProbMap.put(i, perturbationPathDistanceMap.get(i).subList(0, perturbationPathDistanceMap.get(i).size()/2));
            }
        }

        for (Integer key : perturbationPathDistanceHighProbMap.keySet()) {
            List<Double> aPerturbationPathDistances = perturbationPathDistanceHighProbMap.get(key);
            double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(key);

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


//protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
//                                   DataGene[][] startAttractors) {
//
//    double g = 0;
//
//        /*for (int i = 0; i < startAttractors.length; i++){
//            for (int j = 0; j < startAttractors[i].length;j++){
//                System.out.print(startAttractors[i][j]+ "");
//            }
//            System.out.println();
//        }*/
//
//    //n perturbation for sampling and sampling number, some changes could be done here
//    //n = 6, 7 and 8 using sampling, the sampling size is 70, 40, and 20
//    Map<Integer, Integer> numberRequirement = new HashMap<Integer, Integer>(){{put(6, 70); put(7, 40); put(8, 20);}};
//
//    Map<Integer, List<Double>> perturbationPathDistanceMap = new HashMap<>();
//
//    Map<String, Double> perturbationCache = new HashMap<>();
//
//    Map<Integer, List<Double>> simplifiedPerturbationPathDistanceMap = new HashMap<>();
//
//
//    for (DataGene[] startAttractor : startAttractors) {
//        int perturbedLength = (int) GeneralMethods.getOriginalHammingDistance(startAttractor, target);
//        boolean cached = false;
//
//        if (perturbationCache.containsKey(Arrays.deepToString(startAttractor))) {
//            if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
//                perturbationPathDistanceMap.get(perturbedLength).add(perturbationCache.get(Arrays.deepToString(startAttractor)));
//            } else {
//                perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(perturbationCache.get(Arrays.deepToString(startAttractor)))));
//            }
//            cached = true;
//            continue;
//        }
//
//        //System.out.println(perturbationCache);
//
//        DataGene[] currentAttractor = startAttractor;
//        int currentRound = 0;
//        boolean isNotStable;
//        cyclePath.add(startAttractor.clone());
//
//
//
//        List<DataGene[]> cycleStates = new ArrayList<>();
//
//        do {
//            DataGene[] updatedState = this.updateState(currentAttractor, phenotype);
//            isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
//            currentAttractor = updatedState;
//            currentRound += 1;
//            cyclePath.add(updatedState.clone());
//
//            if (perturbationCache.containsKey(Arrays.deepToString(currentAttractor))) {
//
//                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
//                    perturbationPathDistanceMap.get(perturbedLength).add(perturbationCache.get(Arrays.deepToString(currentAttractor)));
//                } else {
//                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(perturbationCache.get(Arrays.deepToString(currentAttractor)))));
//                }
//                cached = true;
//                break;
//            }
//
//            cycleStates.add(updatedState);
//
//        } while (currentRound < this.maxCycle && isNotStable);
//
//        if (cached) {
//            continue;
//        }
//
//        if (currentRound < maxCycle && !(cached)) {
//
//            double hammingDistance = this.getHammingDistance(currentAttractor, target);
//            if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
//                perturbationPathDistanceMap.get(perturbedLength).add(hammingDistance);
//            } else {
//                perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(hammingDistance)));
//            }
//            for (DataGene[] e : cycleStates) {
//                perturbationCache.put(Arrays.deepToString(e), hammingDistance);
//            }
//
//        } else {
//            if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
//                perturbationPathDistanceMap.get(perturbedLength).add((double) target.length);
//            } else {
//                perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList((double) target.length)));
//            }
//            for (DataGene[] e : cycleStates) {
//                perturbationCache.put(Arrays.deepToString(e), (double) target.length);
//            }
//        }
//
//
//        cyclePath.remove(cyclePath.size()-1);
//
//        if (this.printCyclePath) {
//            System.out.println("currentRound: \t" + currentRound);
//            System.out.println("s attractor: \t" + Arrays.toString(startAttractor));
//            System.out.println("target: \t\t" + Arrays.toString(target));
//            System.out.println("initial distance: \t" + this.getHammingDistance(startAttractor, target));
//            System.out.println("final distance: \t" + this.getHammingDistance(currentAttractor, target));
//            List<Double> cycleDists = new ArrayList<>();
//            for (DataGene[] aCyclePath : cyclePath) {
//                cycleDists.add(this.getHammingDistance(aCyclePath, target));
//            }
//            System.out.println(Arrays.deepToString(cyclePath.toArray()));
//            System.out.println(cycleDists);
//            System.out.println();
//        }
//        cycleDists.add(currentRound);
//
//        cyclePath = new ArrayList<>();
//
//    }
//
//    //select from the startattractor directly, speed may be slow
//    //Another approach is sampling directly for specific perturbation n, remove the parameter startAttractors
//
//    for (int i = 0; i <= 10; i++){
//        if (!numberRequirement.keySet().contains(i)){
//            numberRequirement.put(i, perturbationPathDistanceMap.get(i).size());
//        }
//    }
//
//    //select a sublist of of original list (first k elements)
//    for (int i = 0; i <= 10; i++){
//        simplifiedPerturbationPathDistanceMap.put(i, perturbationPathDistanceMap.get(i).subList(0,numberRequirement.get(i)));
//    }
//    System.out.println(simplifiedPerturbationPathDistanceMap);
//
//    for (Integer key : simplifiedPerturbationPathDistanceMap.keySet()) {
//        List<Double> aPerturbationPathDistances = simplifiedPerturbationPathDistanceMap.get(key);
//        double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(key);
//
//        List<Double> gammas = new ArrayList<>();
//        for (Double aPerturbationPathDistance : aPerturbationPathDistances) {
//            double aD = (1 - (aPerturbationPathDistance / ((double) target.length)));
//            double oneGamma = Math.pow(aD, 5);
//            gammas.add(oneGamma);
//        }
//        double averageGamma = GeneralMethods.getAverageNumber(gammas);
//
//        g += perturbedLengthWeight * averageGamma;
//    }
//
//    return 1 - Math.pow(Math.E, (-3 * g));
//}


}
