package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsBalanceAsymmetric;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluateAParticularGRN {
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
        String targetPath = "/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-distributional-x-p00";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);

        FitnessFunction fitnessFunctionESW = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, maxCycle, 500, perturbationRate, thresholds, stride);
        // 0.9462
//        int[] targetGRN = {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 1, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};

        // 0.9543
//        int[] targetGRN = {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 1, 0, 0, 0, -1, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 1, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, -1, 0, 0, -1, 0, -1, 1};

        // 0.8842
//        int[] targetGRN = {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, 0, -1, 1, -1, 0, -1, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 1, 0, 0, 0, -1, 0, 0, 1, -1, -1, 0, -1, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0};

        // 0.7801
//        int[] targetGRN = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, 0, 0, -1, 0, -1, -1, 0, 0, 0, 0, 1, -1, 1, 0, 1, 0, -1, 0, 0, 0, -1, 0, -1, 1, 0, 0, 0, 1, 0, 0, 0, -1, 1, 1, 1, 0, 0, 0, -1, 0, -1, 1, 0, 1, 0, 0};

        // 0.6671
        int[] targetGRN = {0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 1, -1, 0, 0, 0, 0, 1, 0, 0, 1, 1, -1, 0, 0, 0, -1, 0, -1, -1, 1, -1, 0, -1, 0, 0, 1, -1, 0, 1, 0, 0, 1, 1, -1, 0, 0, 0, 0, -1, 0, 0, 0, -1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, -1, 1, -1, 0};

        SimpleMaterial aMaterial = GeneralMethods.convertIntArrayToSimpleMaterial(targetGRN);
        List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                fitnessFunctionZhenyueSym, true, 1700, null, false);
        System.out.println(removeNoEdgeFitnessesZhenyueSym.get(0));
    }
}
