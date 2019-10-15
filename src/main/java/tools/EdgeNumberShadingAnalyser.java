package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficientDouble;

public class EdgeNumberShadingAnalyser {
    private static final int populationSize = 100;
    private static final int tournamentSize = 3;
    private static final int maxGen = 1970;

    public static List<Double> getTournamentSelectionProbabilities(int populationSize, int tournamentSize) {
        List<Double> tournamentProbabilities = new ArrayList<>();
        for (int i=1; i<=(populationSize-tournamentSize+1); i++) {
            tournamentProbabilities.add(binomialCoefficientDouble(populationSize-i, tournamentSize-1) /
                    binomialCoefficientDouble(populationSize, tournamentSize));
        }
        for (int i=0; i<(tournamentSize-1); i++) {
            tournamentProbabilities.add(0.0);
        }
        return tournamentProbabilities;
    }

    public static List<Double> getFitnessList(FitnessFunction fitness, List<String[]> GRNs,
                                              int populationSize, int currentGen) {
        List<Double> fitnessList = new ArrayList<>();
        for (int i=0; i<populationSize; i++) {
            String[] aGRN = GRNs.get(i);
            SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(aGRN);
            List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                    fitness, true, currentGen, null, false);
            fitnessList.add(removeNoEdgeFitnessesZhenyueSym.get(0));
        }

        return fitnessList;
    }

    public static List<Double> getProportionalSelectionProbabilities(FitnessFunction fitness, List<String[]> GRNs,
                                                                     int populationSize, int currentGen) {
        List<Double> fitnessList = getFitnessList(fitness, GRNs, populationSize, currentGen);

        List<Double> normedFitnessList = new ArrayList<>();
        double fitnessSum = fitnessList.stream().mapToDouble(Double::doubleValue).sum();
        for (int i=0; i<populationSize; i++) {
            normedFitnessList.add(fitnessList.get(i) / fitnessSum);
        }

        return normedFitnessList;
    }

    public static int getModMapIdx(int modLowerBound, double modGap, double mod) {
        return (int) ((mod - modLowerBound) / modGap);
    }

    public static List<List<Double>> getModProbabilityShading(FitnessFunction fitness, String selectionType, int maxGen,
                                                               String targetDir) {
        int modUpperBound = 2;
        int modLowerBound = -2;
        double modGap = 0.1;
        List<Double> tournamentProbabilities = getTournamentSelectionProbabilities(populationSize, tournamentSize);

        List<List<Double>> modProbabilitiesAllGen = new ArrayList<>();
        for (int aGen=0; aGen<maxGen; aGen++) {
            if (aGen % 50 == 0) {
                System.out.print("\rProcess generation: " + aGen);
            }
            String test = String.format(targetDir, aGen);
            List<String[]> lines = GeneralMethods.readFileLineByLine(test);

            List<Double> concernedProbabilities = null;
            if (selectionType.equals("tournament")) {
                concernedProbabilities = tournamentProbabilities;
            } else if (selectionType.equals("proportional")) {
                concernedProbabilities = getProportionalSelectionProbabilities(fitness, lines, populationSize, aGen);
            } else {
                throw new NotImplementedException();
            }

            HashMap<Integer, List<Double>> modProbabilityMap = new HashMap<>();
            for (int i = 0; i < 100; i++) {
                String[] lastGRNString = lines.get(i);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
                List<Integer> aListMaterial = GeneralMethods.convertArrayToIntegerList(GeneralMethods.convertSimpleMaterialToIntArray(aMaterial));
                int edgeNum = GeneralMethods.getEdgeNumber(aMaterial);

                double tmpMod = GRNModularity.getGRNModularity(aListMaterial);
                double normedMod = GRNModularity.getNormedMod(tmpMod, edgeNum);
                int modMapIdx = getModMapIdx(modLowerBound, modGap, normedMod);

                if (!modProbabilityMap.containsKey(modMapIdx)) {
                    List<Double> aNewProbabilityList = new ArrayList<>();
                    aNewProbabilityList.add(concernedProbabilities.get(i));
                    modProbabilityMap.put(modMapIdx, aNewProbabilityList);
                } else {
                    modProbabilityMap.get(modMapIdx).add(concernedProbabilities.get(i));
                }
            }

            int maxModIdx = getModMapIdx(modLowerBound, modGap, modUpperBound);
            List<Double> modProbabilities = new ArrayList<>();
            for (int aMod=0; aMod<=maxModIdx; aMod++) {
                if (modProbabilityMap.containsKey(aMod)) {
                    List<Double> aModProbabilities = modProbabilityMap.get(aMod);
                    modProbabilities.add(aModProbabilities.stream().mapToDouble(Double::doubleValue).sum());
                } else {
                    modProbabilities.add(0.0);
                }
            }
            modProbabilitiesAllGen.add(modProbabilities);
        }
        return modProbabilitiesAllGen;
    }

    public static List<List<Double>> getEdgeProbabilityShading(FitnessFunction fitness, String selectionType, int maxGen,
                                                 String targetDir) {
        int edgeUpperBound = 50;
        int edgeLowerBound = 10;
        List<Double> tournamentProbabilities = getTournamentSelectionProbabilities(populationSize, tournamentSize);

        List<List<Double>> edgeProbabilitiesAllGen = new ArrayList<>();
        for (int aGen=0; aGen<maxGen; aGen++) {
            if (aGen % 50 == 0) {
                System.out.print("\rProcess generation: " + aGen);
            }
            String test = String.format(targetDir, aGen);
            List<String[]> lines = GeneralMethods.readFileLineByLine(test);

            List<Double> concernedProbabilities = null;
            if (selectionType.equals("tournament")) {
                concernedProbabilities = tournamentProbabilities;
            } else if (selectionType.equals("proportional")) {
                concernedProbabilities = getProportionalSelectionProbabilities(fitness, lines, populationSize, aGen);
            } else {
                throw new NotImplementedException();
            }

            HashMap<Integer, List<Double>> edgeNumProbabilityMap = new HashMap<>();
            for (int i = 0; i < 100; i++) {
                String[] lastGRNString = lines.get(i);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);

                int edgeNum = GeneralMethods.getEdgeNumber(aMaterial);

                if (!edgeNumProbabilityMap.containsKey(edgeNum)) {
                    List<Double> aNewProbabilityList = new ArrayList<>();
                    aNewProbabilityList.add(concernedProbabilities.get(i));
                    edgeNumProbabilityMap.put(edgeNum, aNewProbabilityList);
                } else {
                    edgeNumProbabilityMap.get(edgeNum).add(concernedProbabilities.get(i));
                }
            }

            List<Double> edgeProbabilities = new ArrayList<>();
            for (int anEdge=edgeLowerBound; anEdge<=edgeUpperBound; anEdge++) {
                if (edgeNumProbabilityMap.containsKey(anEdge)) {
                    List<Double> aEdgeProbabilities = edgeNumProbabilityMap.get(anEdge);
                    edgeProbabilities.add(aEdgeProbabilities.stream().mapToDouble(Double::doubleValue).sum());
                } else {
                    edgeProbabilities.add(0.0);
                }
            }
            edgeProbabilitiesAllGen.add(edgeProbabilities);
        }
        return edgeProbabilitiesAllGen;
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

    public static void main(String[] args) throws IOException {
        String theSelectionType = "tournament";
        String edgeOrMod = "Edge";
        System.out.println("Edge or Mod: " + edgeOrMod);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String outputDirectory = "printing-logs";
        String outputDirectoryPath = outputDirectory + "/edge-shading-log-" + edgeOrMod + "-" + theSelectionType + "-" + dateFormat.format(date) + ".txt";
        PrintWriter writer = new PrintWriter(new FileWriter(outputDirectoryPath));
        writer.print("\n" + edgeOrMod + "\n");

        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);

        String targetPath = "/home/zhenyue-qin/Research/Project-Maotai-Modularity/generated-outputs/30-tournament";
//        String targetPath = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-proportional";
//        String targetPath = "/media/zhenyue-qin/New Volume/Data-Warehouse/Data-Experiments/Project-Maotai/tec-simultaneous-experiments/75-perturbations-proportional";
        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        List<List<List<Double>>> edgePrbabilityShadingList = new ArrayList<>();
        for (File aDirectory : directories) {
            try {
                String targetDir = aDirectory + "/population-phenotypes/all-population-phenotype_gen_%d.lists";

                String lastGenStringGRN = String.format(targetDir, maxGen);
                List<String[]> lastGenStringGRNList = GeneralMethods.readFileLineByLine(lastGenStringGRN);
                String[] eliteInLastGen = lastGenStringGRNList.get(0);
                SimpleMaterial eliteInLastGenMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(eliteInLastGen);
                List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, eliteInLastGenMaterial,
                        fitnessFunctionZhenyueSym, true, 1000, null, false);
                double finalFitness = removeNoEdgeFitnessesZhenyueSym.get(0);

                if (finalFitness > 0) {
                    System.out.println("\nCurrent Final Fitness: " + finalFitness);
                    writer.print("\nCurrent Final Fitness: " + finalFitness + "\n");
                    System.out.println("a directory: " + aDirectory);
                    writer.print("a directory: " + aDirectory + "\n");
                    List<List<Double>> theProbabilityShading;
                    if (edgeOrMod.equals("edge")) {
                        theProbabilityShading = getEdgeProbabilityShading(fitnessFunctionZhenyueSym, theSelectionType, maxGen, targetDir);
                    } else {
                        theProbabilityShading = getModProbabilityShading(fitnessFunctionZhenyueSym, theSelectionType, maxGen, targetDir);
                    }
                    System.out.println("\n" + theProbabilityShading);
                    writer.print("\n" + theProbabilityShading.toString() + "\n");
                    edgePrbabilityShadingList.add(theProbabilityShading);
                }
            } catch (Exception e) {
                e.printStackTrace();
//                break;
            }
        }
        System.out.println(edgePrbabilityShadingList);
        writer.print(edgePrbabilityShadingList.toString() + "\n");
        writer.close();
    }
}
