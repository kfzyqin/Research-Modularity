package experiments.experiment6;

import au.com.bytecode.opencsv.CSVWriter;
import ga.components.materials.GRN;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsBalanceAsymmetric;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final double stride = 0.00;
    private static final int perturbations = 500;

    public static void main(String[] args) throws IOException {
        String targetPath = "/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p00";
//        String targetPath = "/Volumes/LaCie/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p01";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);

        FitnessFunction fitnessFunctionZhenyueASym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.001);

        FitnessFunction fitnessFunctionESWSym = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, 500, maxCycle, perturbationRate, thresholds, 0.00);

        FitnessFunction fitnessFunctionESWAsym = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, 500, maxCycle, perturbationRate, thresholds, 0.00);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        final File file = new File(targetPath + "/" + "volcanoe.csv");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to save csv file.");
        }

        CSVWriter writer = new CSVWriter(new FileWriter(targetPath + "/" + "volcanoe.csv"), '\t');
        String[] entries = ("GRNBeforeRemovalInterModuleEdge#" +
                "GRNAfterRemovalInterModuleEdge#" +
                "Fitness Complete Sampling Symmetric Before Edge Removing#" +
                "Fitness Complete Sampling Symmetric After Edge Removing#" +
                "Fitness Complete Sampling ASymmetric Before Edge Removing#" +
                "Fitness Complete Sampling ASymmetric After Edge Removing#" +
                "Fitness ESW Symmetric Before Edge Removing#" +
                "Fitness ESW Symmetric After Edge Removing#" +
                "Fitness ESW ASymmetric Before Edge Removing#" +
                "Fitness ESW ASymmetric After Edge Removing#").split("#");
        writer.writeNext(entries);

        List<Boolean> increaseAfterAllRemove = new ArrayList<>();
        for (File aDirectory : directories) {
            try {
//                System.out.println("a directory: " + aDirectory);
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
                String[] lastGRNString = lines.get(lines.size() - 1);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
                int interEdgeNo = GeneralMethods.getInterModuleEdgeNumber(GeneralMethods.convertStringArrayToIntArray(lastGRNString));

                List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunctionZhenyueSym, true, 1700, null, false);
                List<Double> removeAllEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunctionZhenyueSym, true, 1700, null, false);
                List<Double> removeNoEdgeFitnessesZhenyueASym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunctionZhenyueASym, true, 1700, null, false);
                List<Double> removeAllEdgeFitnessesZhenyueASym = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunctionZhenyueASym, true, 1700, null, false);
                List<Double> removeNoEdgeFitnessesESWSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunctionESWSym, true, 1700, null, false);
                List<Double> removeAllEdgeFitnessesESWSym = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunctionESWSym, true, 1700, null, false);
                List<Double> removeNoEdgeFitnessesESWASym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunctionESWAsym, true, 1700, null, false);
                List<Double> removeAllEdgeFitnessesESWASym = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunctionESWAsym, true, 1700, null, false);

                List<Double> fitnesses = Arrays.asList(
                        removeNoEdgeFitnessesZhenyueSym.get(0), removeAllEdgeFitnessesZhenyueSym.get(0),
                        removeNoEdgeFitnessesZhenyueASym.get(0), removeAllEdgeFitnessesZhenyueASym.get(0),
                        removeNoEdgeFitnessesESWSym.get(0), removeAllEdgeFitnessesESWSym.get(0),
                        removeNoEdgeFitnessesESWASym.get(0), removeAllEdgeFitnessesESWASym.get(0));

                if (fitnesses.get(1) > 0.9462) {
                    System.out.println(ModularityPathAnalyzer.removeInterModuleEdges(aMaterial).toString());
                    entries = (
                            aMaterial.toString() + "#" +
                                    ModularityPathAnalyzer.removeInterModuleEdges(aMaterial).toString() + "#" +
                                    Double.toString(fitnesses.get(0)) + "#" +
                                    Double.toString(fitnesses.get(1)) + "#" +
                                    Double.toString(fitnesses.get(2)) + "#" +
                                    Double.toString(fitnesses.get(3)) + "#" +
                                    Double.toString(fitnesses.get(4)) + "#" +
                                    Double.toString(fitnesses.get(5)) + "#" +
                                    Double.toString(fitnesses.get(6)) + "#" +
                                    Double.toString(fitnesses.get(7))
                    ).split("#");
                    writer.writeNext(entries);
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound caught! ");
            }
        }
        writer.close();

        System.out.println(increaseAfterAllRemove);
    }
}
