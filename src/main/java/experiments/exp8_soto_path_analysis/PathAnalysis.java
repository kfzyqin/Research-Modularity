package experiments.exp8_soto_path_analysis;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.others.GeneralMethods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathAnalysis {
    private static final double geneMutationRate = 0.05;
    private static final int neighborSize = 500;

    private static final String pathToTheExperiment = "/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/soto-perturbation-recording";

    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int perturbationCycleSize = 75;

    private static final List<Integer> thresholds = Arrays.asList(0, 500);

    /* Parameters of the GRN */
    private static final int maxCycle = 20;
    private static final int edgeSize = 20;
    private static final int perturbations = 75;
    private static final double perturbationRate = 0.15;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int[][] targets = {target1, target2};

        File[] files = new File(pathToTheExperiment).listFiles();
        List<String> directories = new ArrayList<>();
        GeneralMethods.showFiles(files, directories);
        GRNFitnessFunctionMultipleTargets fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);

        for (String aPath : directories) {
            System.out.println(aPath);
            List<List<DataGene[][]>> perturbations = GeneralMethods.getPerturbations(aPath);

            int generation = 1700;
            SimpleMaterial phe_1 = GeneralMethods.getGenerationPhenotype(aPath, generation);
//            SimpleMaterial phe_2 = GeneralMethods.getGenerationPhenotype(aPath, generation+1);

//            for (int i=0; i<perturbations.size(); i++) {
//                for (DataGene[][] aDataGene : perturbations.get(i)) {
//                    System.out.println(Arrays.toString(aDataGene[0]));
//                }
//            }

            System.out.println("original: " + fitnessFunction.evaluate(phe_1, generation, perturbations.get(generation)));
            System.out.println(Arrays.toString(perturbations.get(generation).get(0)));
//
            System.out.println("followed: " + fitnessFunction.evaluate(phe_1, generation, perturbations.get(generation + 1)));
            System.out.println(Arrays.toString(perturbations.get(generation + 1).get(0)));
        }
    }
}
