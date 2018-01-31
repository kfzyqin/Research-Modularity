package ga.others;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LarsonNeighborFitnessAnalyzer {
    private static final double geneMutationRate = 0.05;
    private static final int neighborSize = 500;
//    private static final String pathToTheExperiment = "/Users/qin/Software-Engineering/Chin-GA-Project/thesis-data/different-crossover-mechanism-comparisons/chin-crossover";
    private static final String pathToTheExperiment = "/Users/Chinyuer/Software-Engineering/COMP4560/Chin-GA-Project/generated-outputs/larson-with-perturbation-recording/";

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

    public static List<SimpleMaterial> getMutatedNeighbors(SimpleMaterial original) {
        GRNEdgeMutator mutator = new GRNEdgeMutator(geneMutationRate);
        List<SimpleMaterial> mutatedNeighbors = new ArrayList<>();

        for (int i=0; i<neighborSize; i++) {
            mutatedNeighbors.add(original.copy());
        }

        for (int i=1; i<neighborSize; i++) {
            mutator.mutateAGRN(mutatedNeighbors.get(i));
        }
        return mutatedNeighbors;
    }

    public static Double getMutatedNeighbourFitness(String path, SimpleMaterial aNeighbor, int generation) throws IOException, ClassNotFoundException {
        int[][] targets = {target1, target2};
        GRNFitnessFunctionMultipleTargets fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);
        List<List<DataGene[][]>> perturbations = GeneralMethods.getPerturbations(path);
        return fitnessFunction.evaluate(aNeighbor, generation, perturbations.get(generation));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Double> maxFitnessValues = new ArrayList<>();
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);
        File[] files = new File(pathToTheExperiment).listFiles();
        List<String> directories = new ArrayList<>();
        GeneralMethods.showFiles(files, directories);

        for (String aPath : directories) {
            System.out.println(aPath);
            List<Double> fitnessValues = new ArrayList<>();
            List<SimpleMaterial> mutationNeighbors = getMutatedNeighbors(GeneralMethods.getGenerationPhenotype(aPath, -1));
            for (SimpleMaterial aNeighbor : mutationNeighbors) {
                fitnessValues.add(getMutatedNeighbourFitness(aPath, aNeighbor, 2000));
            }
            maxFitnessValues.add(Collections.max(fitnessValues));
            System.out.println(fitnessValues);
            System.out.println("Original: " + fitnessValues.get(0) + " max: " + Collections.max(fitnessValues.subList(1, fitnessValues.size())));
            System.out.println(fitnessValues.get(0) == Collections.max(fitnessValues));
        }
        System.out.println(maxFitnessValues);
    }
}
