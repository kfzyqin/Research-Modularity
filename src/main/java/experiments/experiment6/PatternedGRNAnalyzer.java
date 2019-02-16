package experiments.experiment6;

import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static experiments.experiment6.EdgeDeletionAnalyzer.getBasicGRN;
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

    public static void main(String[] args) {
        String targetPath = "/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p001";

        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunctionZhenyueSym = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, 0.00);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        int directoryCounter = 0;
        for (File aDirectory : directories) {
            try {
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
//                String aModFile = "/Users/qin/Research/Project-Maotai-Modularity/data/perfect_modular_individuals.txt";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);

                String[] lastGRNString = lines.get(lines.size() - 1);
//                String[] lastGRNString = lines.get(3);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
                int interEdgeNo = GeneralMethods.getInterModuleEdgeNumber(GeneralMethods.convertStringArrayToIntArray(lastGRNString));

                List<Double> removeNoEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(0, aMaterial,
                        fitnessFunctionZhenyueSym, true, 1700, null, false);
                List<Double> removeAllEdgeFitnessesZhenyueSym = ModularityPathAnalyzer.removeEdgeAnalyzer(interEdgeNo, aMaterial,
                        fitnessFunctionZhenyueSym, true, 1700, null, false);

                List<Double> fitnesses = Arrays.asList (
                        removeNoEdgeFitnessesZhenyueSym.get(0), removeAllEdgeFitnessesZhenyueSym.get(0));

//                if (fitnesses.get(0) > 0.9462 && fitnesses.get(1) < fitnesses.get(0)) {
                if (fitnesses.get(0) > 0.9462) {
//                if (true) {
//                    System.out.println("###A New Directory###");
//                    System.out.println("original fitness value: " + fitnesses.get(0));
                    List<Double> originalSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(GeneralMethods.convertStringArrayToIntArray(lastGRNString));
                    int[] noInterModuleGRN = GeneralMethods.getInterModuleRemovedGRN(GeneralMethods.convertStringArrayToIntArray(lastGRNString));
//                    System.out.println("original fitness: " + originalSeparateFitnesses);
//                    printSquareGRN(GeneralMethods.convertStringArrayToIntArray(lastGRNString));
                    if (originalSeparateFitnesses.get(0) > 0.9502 && originalSeparateFitnesses.get(2) > 0.9460) {
                        directoryCounter += 1;

                        System.out.println("\n###A New Directory###");
                        System.out.println("original fitness value: " + fitnesses.get(0));
                        System.out.println("original fitness: " + originalSeparateFitnesses);
//                        break;
                    }

//                    List<Double> noInterModuleSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(noInterModuleGRN);
//                    SimpleMaterial aNoInterModuleMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(noInterModuleGRN));
//                    double noInterModuleFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunctionZhenyueSym).evaluate(aNoInterModuleMaterial, 501);
//                    System.out.println("no inter module fitness: " + noInterModuleFitness);
//                    System.out.println("no inter module: " + noInterModuleSeparateFitnesses);
//                    printSquareGRN(noInterModuleGRN);

                    int[] patternedGRN = GeneralMethods.getPatternedGRN(GeneralMethods.convertStringArrayToIntArray(lastGRNString), true);
                    List<Double> patternedSeparateFitnesses = GeneralMethods.evaluateSeparateModuleFitnesses(patternedGRN);
//                    System.out.println("patterned: " + patternedSeparateFitnesses);
                    SimpleMaterial aNewMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(patternedGRN));
                    double patternedFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunctionZhenyueSym).evaluate(aNewMaterial, 501);
                    System.out.println("patterned fitness: " + patternedSeparateFitnesses);
//                    printSquareGRN(patternedGRN);


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
                System.out.println("Array out of bound caught! ");
            }

        }
        System.out.println("Directory Counter: " + directoryCounter);
    }
}
