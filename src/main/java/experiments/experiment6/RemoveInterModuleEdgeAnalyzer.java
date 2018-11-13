package experiments.experiment6;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveInterModuleEdgeAnalyzer {
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
    private static final double stride = 0.00;
    private static final int perturbations = 500;

    public static void main(String[] args) {
        String targetPath = "/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p00";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        List<Boolean> increaseAfterAllRemove = new ArrayList<>();
        for (File aDirectory : directories) {
            try {
                System.out.println("a directory: " + aDirectory);
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
                String[] lastGRNString = lines.get(lines.size() - 1);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
                int interEdgeNo = GeneralMethods.getInterModuleEdgeNumber(GeneralMethods.convertStringArrayToIntArray(lastGRNString));
                List<Double> removeAllEdgeFitnesses = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunction, true, 1700, null, false);

                List<Double> removeNoEdgeFitnesses = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunction, true, 1700, null, false);

                if (removeAllEdgeFitnesses.size() > 1 || removeNoEdgeFitnesses.size() > 1) {
                    throw new RuntimeException("Removing all edges wrong! ");
                }
                boolean isAnNonDecreasingPath = removeAllEdgeFitnesses.get(removeAllEdgeFitnesses.size() - 1) >= removeNoEdgeFitnesses.get(removeAllEdgeFitnesses.size() - 1);
                increaseAfterAllRemove.add(isAnNonDecreasingPath);
                if (removeAllEdgeFitnesses.get(removeAllEdgeFitnesses.size() - 1) > 0.9462) {
                    System.out.println("######");
                    System.out.println("Original GRN: ");
                    System.out.println(aMaterial);
                    System.out.println("GRN without Inter-Module Edges: ");
                    System.out.println(ModularityPathAnalyzer.removeInterModuleEdges(aMaterial));
                    System.out.println("is an non decreasing path: " + isAnNonDecreasingPath);
                    System.out.println("fitness is: " + removeAllEdgeFitnesses.get(removeAllEdgeFitnesses.size() - 1));
                    System.out.println("#####\n");
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound caught! ");
            }
        }
        System.out.println(increaseAfterAllRemove);
    }
}
