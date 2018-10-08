package experiments.experiment6;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetric;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinations;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterSymmetricLandscapeAnalyzer {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 30;

    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7};

    private static final double stride1 = 0.00;
    private static final double stride2 = 0.01;

    public static void main(String[] args) throws IOException {
        String targetPath = "/Users/qin/Portal/generated-outputs/record-zhenyue-balanced-combinations-p01";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunction1 = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride1);

        FitnessFunction fitnessFunction2 = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride2);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        List<Double> newFitnesses = new ArrayList<>();
        for (File aDirectory : directories) {
            try {
                System.out.println("a directory: " + aDirectory);
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
                String[] lastGRNString = lines.get(lines.size() - 1);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);

                newFitnesses.add(((GRNFitnessFunctionMultipleTargetsAllCombinations) fitnessFunction2).evaluate(
                        aMaterial, 1700));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound caught! ");
            }
        }
        System.out.println(newFitnesses);
//        System.out.println(GeneralMethods.getAverageNumber(newFitnesses));
    }
}
