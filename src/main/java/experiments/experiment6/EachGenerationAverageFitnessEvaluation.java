package experiments.experiment6;

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

import static ga.others.GeneralMethods.getAverageNumber;
import static ga.others.GeneralMethods.getIntAverageNumber;
import static ga.others.GeneralMethods.printSquareGRN;

public class EachGenerationAverageFitnessEvaluation {
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

    private static final int usedSize = 100;

    public static void main(String[] args) {
//        String targetPath = "/media/zhenyue-qin/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p01";
//        String targetPath = "/media/zhenyue-qin/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/original_esw_p01";
        String targetPath = "/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/distributional-p00";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        FitnessFunction fitnessFunctionESW = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, maxCycle, 500, perturbationRate, thresholds, stride);

        FitnessFunction fitnessFunctionToUse = null;

        String fitnessToUseStr = "ESW";
        if (fitnessToUseStr.equals("Zhenyue")) {
            fitnessFunctionToUse = fitnessFunctionZhenyueSym;
        } else if (fitnessToUseStr.equals("ESW")) {
            fitnessFunctionToUse = fitnessFunctionESW;
        }

        System.out.println("Fitness to use string: " + fitnessToUseStr);
        System.out.println("Stride to use: " + stride);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        int directoryCounter = 0;

        List<Double> cycleDistAll = new ArrayList<>();
        double[] experimentAvgFitnesses = null;

        int fileNumberCounter = 0;

        List<Double> finalGenFitnesses = new ArrayList<>();
        List<Double> fitnessStdDev = new ArrayList<>();

        for (File aDirectory : directories) {
            System.out.println("a directory: " + aDirectory);

            try {
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
//                String aModFile = "/Users/qin/Research/Project-Maotai-Modularity/data/perfect_modular_individuals.txt";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);

                List<Double> oneTrialFitnesses = new ArrayList<>();

                for (int aGen=0; aGen<lines.size(); aGen++) {
                    SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lines.get(aGen));

                    List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                            fitnessFunctionToUse, true, aGen, null, false);


                    List<Double> fitnesses = Arrays.asList(
                            removeNoEdgeFitnessesZhenyueSym.get(0));

                    oneTrialFitnesses.add(fitnesses.get(0));
                }

                if (experimentAvgFitnesses == null) {
                    experimentAvgFitnesses = new double[oneTrialFitnesses.size()];
                }
                for (int i=0; i<oneTrialFitnesses.size(); i++) {
                    experimentAvgFitnesses[i] += oneTrialFitnesses.get(i);
                }

                finalGenFitnesses.add(oneTrialFitnesses.get(oneTrialFitnesses.size()-1));
                fitnessStdDev.add(GeneralMethods.getStDev(oneTrialFitnesses));

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound caught! ");
            }
            fileNumberCounter += 1;
            if (fileNumberCounter >= usedSize) {
                break;
            }
        }

        for (int i=0; i<experimentAvgFitnesses.length; i++) {
            experimentAvgFitnesses[i] = (experimentAvgFitnesses[i] / usedSize);
        }

        System.out.println(cycleDistAll);
        System.out.println(Arrays.toString(experimentAvgFitnesses));

        System.out.println(finalGenFitnesses);

    }
}
