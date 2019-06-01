package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.others.GeneralMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DistanceCorrelation {
    public static double getCorrelation(List<Double> xs, List<Double> ys) {
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

    static final double mutationRate = 1;
    public static void main(String[] args) {
        int[][] targets = {target1, target2};

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);

        String aModFile = "./data/perfect_modular_individuals.txt";
        List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
        SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lines.get(0));

        List<Double> correlations = new ArrayList<>();
        for (int corID=0; corID<100; corID++) {
            System.out.println("Current correlation ID: " + corID);
            List<Double> mutatedFitnesses = new ArrayList<>();
            mutatedFitnesses.add(fitnessFunction.evaluate(aMaterial));
            GRNEdgeMutator edgeGeneMutator = new GRNEdgeMutator(mutationRate);

            int mutatingTimes = 100;
            for (int i = 0; i < mutatingTimes; i++) {
                edgeGeneMutator.mutateAGRN(aMaterial);
                mutatedFitnesses.add(fitnessFunction.evaluate(aMaterial));
            }

            List<Double> dists = new ArrayList<>();

            for (int i = 0; i < mutatedFitnesses.size(); i++) {
                dists.add((double) i);
            }
            double aCorrelation = getCorrelation(mutatedFitnesses, dists);
            correlations.add(aCorrelation);
        }

        System.out.println(correlations);
    }
}
