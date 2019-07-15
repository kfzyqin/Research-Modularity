package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.others.GeneralMethods;

import java.util.*;
import java.util.stream.IntStream;

public class DistanceCorrelation {
    public static double getCorrelation(List<Double> xs, List<Double> ys) {
        System.out.print("Correlation xs: ");
        System.out.println(xs);
        System.out.print("Correlation ys: ");
        System.out.println(ys);
        double[] xsArray = new double[xs.size()];
        double[] ysArray = new double[ys.size()];
        for (int i=0; i<xs.size(); i++) {
            xsArray[i] = xs.get(i);
            ysArray[i] = ys.get(i);
        }
        return getCorrelation(xsArray, ysArray);
    }

    public static double getCorrelation(double[] xs, double[] ys) {
        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = xs.length;

        for(int i = 0; i < n; ++i) {
            double x = xs[i];
            double y = ys[i];

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        // covariation
        double cov = sxy / n - sx * sy / n / n;
        // standard error of x
        double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
        // standard error of y
        double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

        // correlation is just a normalized covariation
        return cov / sigmax / sigmay;
    }

    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 30;
    private static final int perturbations = 500;
    private static final double perturbationRate = 0.15;
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final double stride = 0.00;

    static final double mutationRate = 0.05;
    public static void main(String[] args) {
        int[][] targets = {target1, target2};

        GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
//                targets, maxCycle, perturbations, perturbationRate, thresholds);

//        String aModFile = "./data/perfect_modular_individuals.txt";
//        List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
//        SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lines.get(0));

        int[] targetGRN = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0};
        SimpleMaterial aMaterial = GeneralMethods.convertIntArrayToSimpleMaterial(targetGRN);

        List<Double> mutationNumCorrelations = new ArrayList<>();
        List<Double> posCorrelations = new ArrayList<>();
        List<Double> absCorrelations = new ArrayList<>();

        Map<Integer, List<Double>> mutateNumFitMap = new HashMap<>();
        Map<Integer, List<Double>> mutateNumFitChangeMap = new HashMap<>();

        for (int corID=0; corID<100; corID++) {
            List<Double> posList = new ArrayList<>();
            List<Double> absList = new ArrayList<>();

            System.out.println("Current correlation ID: " + corID);
            List<Double> mutatedFitnesses = new ArrayList<>();
//            mutatedFitnesses.add(fitnessFunction.evaluate(aMaterial));

//            if (!mutateNumFitMap.containsKey(0)) {
//                mutateNumFitMap.put(0, new ArrayList<>());
//            }
//            mutateNumFitMap.get(0).add(mutatedFitnesses.get(mutatedFitnesses.size()-1));

            GRNEdgeMutator edgeGeneMutator = new GRNEdgeMutator(mutationRate);
            Set<SimpleMaterial> previousMaterials = new HashSet<>();


            int mutatingTimes = 20;
            int mutatedTimes = 0;
            SimpleMaterial aMaterialCopy = aMaterial.copy();
            SimpleMaterial aMaterialCopyOriginal = aMaterial.copy();
            posList.add(GeneralMethods.getSimpleMaterialPositionDifference(aMaterialCopy, aMaterialCopy));
            absList.add(GeneralMethods.getSimpleMaterialAbsDifference(aMaterialCopy, aMaterialCopy));

            SimpleMaterial previousMaterial = aMaterialCopy.copy();
            do{
                if (!previousMaterials.contains(aMaterialCopy)) {
                    posList.add(GeneralMethods.getSimpleMaterialPositionDifference(aMaterialCopyOriginal, aMaterialCopy));
                    absList.add(GeneralMethods.getSimpleMaterialAbsDifference(aMaterialCopyOriginal, aMaterialCopy));
                    previousMaterials.add(aMaterialCopy.copy());
                    previousMaterial = aMaterialCopy.copy();
                    mutatedFitnesses.add(fitnessFunction.evaluate(aMaterialCopy, 502));

                    if (mutatedTimes >= 1) {
                        if (!mutateNumFitChangeMap.containsKey(mutatedTimes)) {
                            mutateNumFitChangeMap.put(mutatedTimes, new ArrayList<>());
                        }
                        mutateNumFitChangeMap.get(mutatedTimes).add(mutatedFitnesses.get(mutatedFitnesses.size() - 1) - mutatedFitnesses.get(mutatedFitnesses.size() - 2));
                    }

                    if (!mutateNumFitMap.containsKey(mutatedTimes)) {
                        mutateNumFitMap.put(mutatedTimes, new ArrayList<>());
                    }
                    mutateNumFitMap.get(mutatedTimes).add(mutatedFitnesses.get(mutatedFitnesses.size()-1));

                    mutatedTimes += 1;
                }
                edgeGeneMutator.mutateAGRN(aMaterialCopy);
            } while (mutatedTimes < mutatingTimes);

            List<Double> dists = new ArrayList<>();

            for (int i = 0; i < mutatedFitnesses.size(); i++) {
                dists.add((double) i);
            }
            double aMutationNumCorrelation = getCorrelation(mutatedFitnesses, dists);
            double aPosCorrelation = getCorrelation(mutatedFitnesses, posList);
            double aAbsCorrelation = getCorrelation(mutatedFitnesses, absList);
            mutationNumCorrelations.add(aMutationNumCorrelation);
            posCorrelations.add(aPosCorrelation);
            absCorrelations.add(aAbsCorrelation);
        }

        System.out.println(mutationNumCorrelations);
        System.out.println(posCorrelations);
        System.out.println(absCorrelations);

        System.out.println(mutateNumFitMap);
        System.out.println(mutateNumFitChangeMap);

        List<Double> avgFitValues = new ArrayList<>();
        List<Double> avgFitStdev = new ArrayList<>();
        List<Double> avgDifValues = new ArrayList<>();
        List<Double> avgDivStdev = new ArrayList<>();

        List<Integer> fitIdxes = new ArrayList<>(mutateNumFitMap.keySet());
        Collections.sort(fitIdxes);
        for (Integer e : fitIdxes) {
            avgFitValues.add(GeneralMethods.getAverageNumber(mutateNumFitMap.get(e)));
            avgFitStdev.add(GeneralMethods.getStDev(mutateNumFitMap.get(e)));
        }

        List<Integer> difIdxes = new ArrayList<>(mutateNumFitChangeMap.keySet());
        Collections.sort(difIdxes);
        for (Integer e : difIdxes) {
            avgDifValues.add(GeneralMethods.getAverageNumber(mutateNumFitChangeMap.get(e)));
            avgDivStdev.add(GeneralMethods.getStDev(mutateNumFitChangeMap.get(e)));
        }

        System.out.println(avgFitValues);
        System.out.println(avgFitStdev);
        System.out.println(avgDifValues);
        System.out.println(avgDivStdev);
    }
}
