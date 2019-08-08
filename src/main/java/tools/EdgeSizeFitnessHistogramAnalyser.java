package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static tools.EdgeNumberShadingAnalyser.*;

public class EdgeSizeFitnessHistogramAnalyser {
    private static final int populationSize = 100;

    public static int getFitnessIdx(double fitness, double fitnessSep) {
        int maxIdx = (int) (0.9462 / fitnessSep) + 1;
        if (fitness > 0.9462) {
//            System.out.println("caught max fitness");
            return maxIdx;
        } else {
            return (int) (fitness / fitnessSep);
        }
    }

    public static int getMaxFitnessIdx(double fitnessSep) {
        int maxIdx = (int) (0.9462 / fitnessSep) + 1;
        return maxIdx;
    }

    public static List<Double> getFitnessHistogramXAxis(int startingIdx, double fitnessSep) {
        int maxFitIdx = getMaxFitnessIdx(fitnessSep);
        List<Double> rtnXAxis = new ArrayList<>();
        for (int i=startingIdx; i<=maxFitIdx; i++) {
            rtnXAxis.add((double) i * fitnessSep);
        }
        return rtnXAxis;
    }

    public static List<Double> getFitnessIdxHistogramXAxis(int startIdx) {
        int maxFitIdx = getMaxFitnessIdx(fitnessSep);
        List<Double> rtnXAxis = new ArrayList<>();
        for (int i=startIdx; i<=maxFitIdx; i++) {
            rtnXAxis.add((double) i);
        }
        return rtnXAxis;
    }

    public static List<Double> getFitnessHistogramSelectionProbabilityList(Map<Integer, Double> fitnessProbabilityMap,
                                                                           int startIdx, double fitnessSep) {
        int maxFitIdx = getMaxFitnessIdx(fitnessSep);
        List<Double> rtnFitnesses = new ArrayList<>();
        for (int i=startIdx; i<=maxFitIdx; i++) {
            if (fitnessProbabilityMap.containsKey(i)) {
                rtnFitnesses.add(fitnessProbabilityMap.get(i));
            } else {
                rtnFitnesses.add(0.0);
            }
        }
        return rtnFitnesses;
    }

    public static List<Double> getPopulationEdgeNumbers(List<String[]> lines) {
        List<Double> populationEdgeNums = new ArrayList<>();
        for (int i=0; i<lines.size(); i++) {
            String[] lastGRNString = lines.get(i);
            SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
            int edgeNum = GeneralMethods.getEdgeNumber(aMaterial);
            populationEdgeNums.add((double) edgeNum);
        }
        return populationEdgeNums;
    }

    public static Map<Integer, Double> getFitnessProbabilityMap(FitnessFunction fitness, String selectionType, String targetDir,
                                                                int populationSize, double fitnessSep, int aGen, String operationType) {
        Map<Integer, List<Double>> fitnessProbabilityMap = new HashMap<>();
        String phenos = String.format(targetDir, aGen);
        List<String[]> lines = GeneralMethods.readFileLineByLine(phenos);

        List<Double> concernedProbabilities;
        if (selectionType.equals("tournament")) {
            concernedProbabilities = getTournamentSelectionProbabilities(populationSize, tournamentSize);
        } else if (selectionType.equals("proportional")) {
            concernedProbabilities = getProportionalSelectionProbabilities(fitness, lines, populationSize, aGen);
        } else if (selectionType.equals("edge number")) {
            concernedProbabilities = getPopulationEdgeNumbers(lines);
        } else {
            throw new NotImplementedException();
        }

        List<Double> fitnessList = getFitnessList(fitness, lines, populationSize, aGen);

        for (int anIndIdx=0; anIndIdx<populationSize; anIndIdx++) {
            double indFitness = fitnessList.get(anIndIdx);
            double indSelectionProbability = concernedProbabilities.get(anIndIdx);
            int fitnessIdx = getFitnessIdx(indFitness, fitnessSep);
            if (fitnessProbabilityMap.containsKey(fitnessIdx)) {
                fitnessProbabilityMap.get(fitnessIdx).add(indSelectionProbability);
            } else {
                List<Double> aSelectionProbabilityList = new ArrayList<>();
                aSelectionProbabilityList.add(indSelectionProbability);
                fitnessProbabilityMap.put(fitnessIdx, aSelectionProbabilityList);
            }
        }

        return reduceFitnessProbabilityMap(fitnessProbabilityMap, operationType);
    }

    public static Map<Integer, Double> reduceFitnessProbabilityMap(Map<Integer, List<Double>> fitnessProbabilityMap,
                                                           String operationType) {
        Map<Integer, Double> rtnMap = new HashMap<>();

        if (operationType.equals("sum")) {
            for (Integer e : fitnessProbabilityMap.keySet()) {
                rtnMap.put(e, fitnessProbabilityMap.get(e).stream().mapToDouble(Double::doubleValue).sum());
            }
        } else if (operationType.equals("avg")) {
            for (Integer e : fitnessProbabilityMap.keySet()) {
                rtnMap.put(e, GeneralMethods.getAverageNumber(fitnessProbabilityMap.get(e)));
            }
        } else {
            throw new NotImplementedException();
        }
        return rtnMap;
    }

    public static List<Double> averageListOfList(List<List<Double>> lists) {
        List<Double> rtn = new ArrayList<>();
        for (List<Double> aList : lists) {
            rtn.add(GeneralMethods.getAverageNumber(aList));
        }
        return rtn;
    }

    public static List<List<Double>> overallProportionalSelectionList = new ArrayList<>();
    public static List<List<Double>> overallTournamentSelectionList = new ArrayList<>();
    public static void plotHistogram(FitnessFunction fitness, String targetTournament, String targetProportional,
                                     int populationSize, double fitnessSep, int aGen, String selectionType, String operationType) throws IOException {

        String tournamentType = "tournament";
        String proportionalType = "proportional";
        if (selectionType.equals("edge number")) {
            tournamentType = proportionalType = selectionType;
        }

        Map<Integer, Double> tournamentProbabilityMap = getFitnessProbabilityMap(fitness, tournamentType,
                targetTournament, populationSize, fitnessSep, aGen, operationType);
        List<Double> tournamentSelectionList = getFitnessHistogramSelectionProbabilityList(tournamentProbabilityMap, startingIdx, fitnessSep);

        Map<Integer, Double> proportionalProbabilityMap = getFitnessProbabilityMap(fitness, proportionalType,
                targetProportional, populationSize, fitnessSep, aGen, operationType);
        List<Double> proportionalSelectionList = getFitnessHistogramSelectionProbabilityList(proportionalProbabilityMap, startingIdx, fitnessSep);

        for (int i=0; i<proportionalSelectionList.size(); i++) {
            if (i >= overallProportionalSelectionList.size()) {
                overallProportionalSelectionList.add(new ArrayList<>());
                overallProportionalSelectionList.get(i).add(proportionalSelectionList.get(i));
            } else {
                overallProportionalSelectionList.get(i).add(proportionalSelectionList.get(i));
            }
            if (i >= overallTournamentSelectionList.size()) {
                overallTournamentSelectionList.add(new ArrayList<>());
                overallTournamentSelectionList.get(i).add(tournamentSelectionList.get(i));
            } else {
                overallTournamentSelectionList.get(i).add(tournamentSelectionList.get(i));
            }
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

    private static final double fitnessSep = 0.0001;
    private static int startingIdx = (int) (0.9 / fitnessSep);

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();
    private static String outputDirectory = "generated-images/selection-fitness";

    private static String operationType = "avg";

    public static void main(String[] args) throws IOException {
        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);

        String targetPathProportional = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-proportional";
        File[] directoriesProportional = new File(targetPathProportional).listFiles(File::isDirectory);
        String targetPathTournament = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Data/distributional-p00";
        File[] directoriesTournament = new File(targetPathTournament).listFiles(File::isDirectory);

        String selectionType = "edge number";
        String outputDirectoryPath = outputDirectory + "/" + dateFormat.format(date) + "_" + selectionType;

        for (int aGen=0; aGen<2000; aGen+=10) {
            overallProportionalSelectionList = new ArrayList<>();
            overallTournamentSelectionList = new ArrayList<>();
            for (int fileIdx = 10; fileIdx < 13; fileIdx++) {
                try {
                    File aDirProportional = directoriesProportional[fileIdx];
                    File aDirTournament = directoriesTournament[fileIdx];
                    String targetDirProportional = aDirProportional + "/population-phenotypes/all-population-phenotype_gen_%d.lists";
                    String targetTournament = aDirTournament + "/population-phenotypes/all-population-phenotype_gen_%d.lists";
                    plotHistogram(fitnessFunctionZhenyueSym, targetTournament, targetDirProportional, populationSize, fitnessSep,
                            aGen, selectionType, operationType);
                } catch (Exception e) {
                    continue;
                }
            }

            List<Double> avgOverallProportionalSelectionList = averageListOfList(overallProportionalSelectionList);
            List<Double> avgOverallTournamentSelectionList = averageListOfList(overallTournamentSelectionList);

            List<Double> histogramXAxis = getFitnessHistogramXAxis(startingIdx, fitnessSep);

            ProcessBuilder PB1 = new ProcessBuilder("/home/zhenyue-qin/.conda/envs/modularity/bin/python",
                    "python-tools/java_posts/java_histogram_mate.py",
                    histogramXAxis.toString(),
                    avgOverallTournamentSelectionList.toString(),
                    avgOverallProportionalSelectionList.toString(),
                    Integer.toString(aGen),
                    outputDirectoryPath);

            Process p2 = PB1.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String ret = in.readLine();
            System.out.println(ret);
        }


    }
}
