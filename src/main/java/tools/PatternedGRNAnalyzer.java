package tools;

import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsBalanceAsymmetric;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;
import tools.GRNModularity;
import tools.Modularity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static experiments.experiment6.EdgeDeletionAnalyzer.getBasicGRN;
import static ga.others.GeneralMethods.getAverageNumber;
import static ga.others.GeneralMethods.getIntAverageNumber;
import static ga.others.GeneralMethods.printSquareGRN;

public class PatternedGRNAnalyzer {
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
    private static final double stride = 0.01;

    public static void main(String[] args) {
        String targetPath = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Data/distributional-p00";
//        String targetPath = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-proportional";
//        String targetPath = "/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/original_esw_p00";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);

        FitnessFunction fitnessFunctionESW = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, maxCycle, 500, perturbationRate, thresholds, stride);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        List<Double> fits = new ArrayList<>();

        int directoryCounter = 0;

        List<Double> cycleDistAll = new ArrayList<>();
        List<Double> originalFitnessLists = new ArrayList<>();

        int improvedCount = 0;
        int maxed = 0;

        int fileNumberCounter = 0;

        double maxMod = -1;
        double maxEdgeNum = 0;
        double minMod = 1;
        double minEdgeNum = 100;

        int targetGeneration = 1000;

        List<Double> edgeNumbers = new ArrayList<>();

        for (File aDirectory : directories) {
            boolean excepted = false;
            try {
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
//                String aModFile = "/Users/qin/Research/Project-Maotai-Modularity/data/perfect_modular_individuals.txt";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);

                String[] lastGRNString = lines.get(lines.size() - targetGeneration);
//                String[] lastGRNString = lines.get(3);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);

                List<Integer> materialList = GeneralMethods.convertArrayToIntegerList(GeneralMethods.convertSimpleMaterialToIntArray(aMaterial));
                double mod = GRNModularity.getGRNModularity(materialList);

                int interEdgeNo = GeneralMethods.getInterModuleEdgeNumber(GeneralMethods.convertStringArrayToIntArray(lastGRNString));

                List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunctionZhenyueSym, true, 1700, null, false);
                List<Double> removeAllEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunctionZhenyueSym, true, 1700, null, false);

                List<Double> fitnesses = Arrays.asList (
                        removeNoEdgeFitnessesZhenyueSym.get(0), removeAllEdgeFitnessesZhenyueSym.get(0));

//                if (fitnesses.get(0) < 0.9462) {
//                if (fitnesses.get(0) < 0.9 || fitnesses.get(0) > 0.9462) {
                if (fitnesses.get(0) < 0.7) {
                    continue;
                }

                System.out.println("\n###A New Directory###");
                System.out.print("fitnesses: ");
                System.out.println(fitnesses);
                if (fitnesses.get(0) > 0.9462) {
                    maxed += 1;
                }

                fits.add(fitnesses.get(0));

                int edgeNum = GeneralMethods.getEdgeNumber(aMaterial);
                System.out.println("Edge number: " + edgeNum);
                if (edgeNum > maxEdgeNum) {
                    maxEdgeNum = edgeNum;
                }
                if (edgeNum < minEdgeNum) {
                    minEdgeNum = edgeNum;
                }

                edgeNumbers.add((double) edgeNum);

                double normedMod = GRNModularity.getNormedMod(mod, edgeNum);
                if (normedMod > maxMod) {
                    maxMod = normedMod;
                }
                if (normedMod < minMod) {
                    minMod = normedMod;
                }

                System.out.println("Modularity: " + normedMod);
                System.out.println("mod: " + mod);

                System.out.println("a material");
                System.out.println(aMaterial);

                if (fitnesses.get(0) > 0.9462 && fitnesses.get(1) < fitnesses.get(0)) {
                    improvedCount += 1;
                }
//                if (fitnesses.get(0) > 0.9462) {
                if (true) {
//                    System.out.println("###A New Directory###");
//                    System.out.println("original fitness value: " + fitnesses.get(0));
                    List<Double> originalSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(GeneralMethods.convertStringArrayToIntArray(lastGRNString), false);
                    int[] noInterModuleGRN = GeneralMethods.getInterModuleRemovedGRN(GeneralMethods.convertStringArrayToIntArray(lastGRNString));
//                    System.out.println("original fitness: " + originalSeparateFitnesses);
//                    printSquareGRN(GeneralMethods.convertStringArrayToIntArray(lastGRNString));

//                    if (originalSeparateFitnesses.get(0) > 0.9502 && originalSeparateFitnesses.get(2) > 0.9460) {
                    if (true) {
                        printSquareGRN(aMaterial);

                        FitnessFunction originalFitness = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);
//                        ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue) originalFitness).printCyclePath = true;

                        ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue) originalFitness).evaluate(aMaterial, 200);
                        cycleDistAll.add(getIntAverageNumber(((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue) originalFitness).cycleDists));
                        originalFitnessLists.add(fitnesses.get(0));

                        directoryCounter += 1;
                        GeneralMethods.evaluateSeparateModuleFitnesses(GeneralMethods.convertStringArrayToIntArray(lastGRNString), false);
                        System.out.println("original fitness value: " + fitnesses.get(0));
                        System.out.println("original fitness: " + originalSeparateFitnesses);

//                        printSquareGRN(noInterModuleGRN);

                        System.out.println("@@@@@@ Customing @@@@@@");
                        int[] customGRN = GeneralMethods.getCustomGRN();
                        List<Double> sameColumnPatternedSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(customGRN, false);
                        SimpleMaterial aCustomMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(customGRN));
                        double sameCustomFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunctionZhenyueSym).evaluate(aCustomMaterial, 501);
                        System.out.println("custom fitness: " + sameColumnPatternedSeparateFitnesses);
//                        printSquareGRN(customGRN);
//                        GeneralMethods.evaluateSeparateModuleFitnesses(customGRN, true);

//                        break;
                    }



//                    List<Double> noInterModuleSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(noInterModuleGRN);
//                    SimpleMaterial aNoInterModuleMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(noInterModuleGRN));
//                    double noInterModuleFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunctionZhenyueSym).evaluate(aNoInterModuleMaterial, 501);
//                    System.out.println("no inter module fitness: " + noInterModuleFitness);
//                    System.out.println("no inter module: " + noInterModuleSeparateFitnesses);
//                    printSquareGRN(noInterModuleGRN);

//                    int[] patternedGRN = GeneralMethods.getPatternedGRN(GeneralMethods.convertStringArrayToIntArray(lastGRNString), true);
//                    List<Double> patternedSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(patternedGRN);
////                    System.out.println("patterned: " + patternedSeparateFitnesses);
//                    SimpleMaterial aNewMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(patternedGRN));
//                    double patternedFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunctionZhenyueSym).evaluate(aNewMaterial, 501);
//                    System.out.println("patterned fitness: " + patternedSeparateFitnesses);
////                    printSquareGRN(patternedGRN);


//                    if ((patternedFitness < fitnesses.get(0))) {
//                        if (!Arrays.equals(patternedGRN, noInterModuleGRN)) {
//                            System.out.println("deleted");
//                        }
////                        if (!Arrays.equals(patternedGRN, noInterModuleGRN)) {
//                            System.out.println("@@@ Patterned Fitness: " + patternedFitness +
//                                    "\nnoInterModuleFitness: " + noInterModuleFitness + "\noriginal: " + fitnesses.get(0));
////                        }
//                    }


                }

            } catch (ArrayIndexOutOfBoundsException e) {
                excepted = true;
                System.out.println("Array out of bound caught! ");
            }
            if (!excepted) {
                fileNumberCounter += 1;
            }
            if (fileNumberCounter > 100) {
                break;
            }
        }

        System.out.println("improved count: " + improvedCount);

        System.out.println(cycleDistAll);
        System.out.println(originalFitnessLists);

        System.out.println("Directory Counter: " + directoryCounter);

        System.out.println("maxed: " + maxed);

        System.out.println("Max Mod: " + maxMod);
        System.out.println("Min Mod: " + minMod);
        System.out.println("Max Edge Num: " + maxEdgeNum);
        System.out.println("Min Edge Num: " + minEdgeNum);
        System.out.println("Mean Fit: " + GeneralMethods.getAverageNumber(fits));

        System.out.println("Average edge number: " + GeneralMethods.getAverageNumber(edgeNumbers));
    }
}
