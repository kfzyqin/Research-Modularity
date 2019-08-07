package tools;

import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.*;

import static tools.EdgeNumberShadingAnalyser.*;

public class EdgeSizeFitnessHistogramAnalyser {
    private static final int populationSize = 100;

    public static int getFitnessIdx(double fitness, double fitnessSep) {
        int maxIdx = (int) (0.9462 / fitnessSep) + 1;
        if (fitness > 0.9462) {
            return maxIdx;
        } else {
            return (int) (fitness / fitnessSep);
        }
    }

    public static int getMaxFitnessIdx(double fitnessSep) {
        int maxIdx = (int) (0.9462 / fitnessSep) + 1;
        return maxIdx;
    }

    public static List<Double> getFitnessHistogramSelectionProbabilityList(Map<Integer, Double> fitnessProbabilityMap) {
        int maxFitIdx = Collections.max(fitnessProbabilityMap.keySet());
        List<Double> rtnFitnesses = new ArrayList<>();
        for (int i=0; i<=maxFitIdx; i++) {
            if (fitnessProbabilityMap.containsKey(i)) {
                rtnFitnesses.add(fitnessProbabilityMap.get(i));
            } else {
                rtnFitnesses.add(0.0);
            }
        }
        return rtnFitnesses;
    }

    public static void tmp(FitnessFunction fitness, String selectionType, String targetDir, int populationSize, double fitnessSep) {
        List<Double> tournamentProbabilities = getTournamentSelectionProbabilities(populationSize, tournamentSize);

        for (int aGen=1999; aGen<2000; aGen++) {
            Map<Integer, Double> fitnessProbabilityMap = new HashMap<>();
            String phenos = String.format(targetDir, aGen);
            List<String[]> lines = GeneralMethods.readFileLineByLine(phenos);

            List<Double> concernedProbabilities;
            if (selectionType.equals("tournament")) {
                concernedProbabilities = tournamentProbabilities;
            } else if (selectionType.equals("proportional")) {
                concernedProbabilities = getProportionalSelectionProbabilities(fitness, lines, populationSize, aGen);
            } else {
                throw new NotImplementedException();
            }

            List<Double> fitnessList = getFitnessList(fitness, lines, populationSize, aGen);

            for (int anIndIdx=0; anIndIdx<populationSize; anIndIdx++) {
                double indFitness = fitnessList.get(anIndIdx);
                double indSelectionProbability = concernedProbabilities.get(anIndIdx);
                int fitnessIdx = getFitnessIdx(indFitness, fitnessSep);
                if (fitnessProbabilityMap.containsKey(fitnessIdx)) {
                    fitnessProbabilityMap.put(fitnessIdx, fitnessProbabilityMap.get(fitnessIdx) + indSelectionProbability);
                } else {
                    fitnessProbabilityMap.put(fitnessIdx, indSelectionProbability);
                }
            }
            System.out.println(getFitnessHistogramSelectionProbabilityList(fitnessProbabilityMap));
        }
    }

    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 100;

    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final double stride = 0.00;
    private static final int[][] targets = {target1, target2};

    private static final int tournamentSize = 3;
    private static final int maxGen = 2000;

    private static final double fitnessSep = 0.02;
    public static void main(String[] args) {
        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);
        String theSelectionType = "proportional";

//        String targetPath = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Data/distributional-p00";
        String targetPath = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-proportional";
        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        for (File aDirectory : directories) {
            String targetDir = aDirectory + "/population-phenotypes/all-population-phenotype_gen_%d.lists";
            tmp(fitnessFunctionZhenyueSym, theSelectionType, targetDir, populationSize, fitnessSep);
            break;
        }
    }
}
