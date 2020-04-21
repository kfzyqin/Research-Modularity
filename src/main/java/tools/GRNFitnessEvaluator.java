package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsBalanceAsymmetric;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ga.others.GeneralMethods.getIntAverageNumber;
import static ga.others.GeneralMethods.printSquareGRN;

public class GRNFitnessEvaluator {
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

    public static void main(String[] args) {
        String targetPath = "/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-x-p00";

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

        int targetGeneration = 750;

        List<Double> edgeNumbers = new ArrayList<>();



        for (File aDirectory : directories) {
            boolean excepted = false;
            try {
                String aModFile = aDirectory + "/" + "population-phenotypes" + "/" + "all-population-phenotype_gen_" + targetGeneration + ".lists";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
                for (String[] aLine : lines) {
                    SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(aLine);
                    List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                            fitnessFunctionESW, true, 1700, null, false);
                    fits.add(removeNoEdgeFitnessesZhenyueSym.get(0));

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
        System.out.println(fits);
        System.out.println("Mean Fit: " + GeneralMethods.getAverageNumber(fits));

        System.out.println("Average edge number: " + GeneralMethods.getAverageNumber(edgeNumbers));
    }
}
