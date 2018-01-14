package ga.others;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LarsonNeighborFitnessAnalyzer {
    private static final double geneMutationRate = 0.05;
    private static final int neighborSize = 5;
//    private static final String pathToTheExperiment = "/Users/qin/Software-Engineering/Chin-GA-Project/thesis-data/different-crossover-mechanism-comparisons/chin-crossover";
    private static final String pathToTheExperiment = "/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/larson-with-perturbation-recording";

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

    public static SimpleMaterial getLastGenerationPhenotype(String path) throws IOException {
        ProcessBuilder postPB =
                new ProcessBuilder("python", "./python-tools/last_generation_phenotype_fetcher.py",
                        path);
        Process p2 = postPB.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        String ret = in.readLine();
        ret = ret.replace(" ", "");
        return (GeneralMethods.convertStringArrayToSimpleMaterial(ret.split(",")));
    }

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
        String fileName= path + "/" + "Haploid-GRN-Matrix.per";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            List<List<DataGene[][]>> perturbations = (List<List<DataGene[][]>>) ois.readObject();
            ois.close();
            return fitnessFunction.evaluate(aNeighbor, generation, perturbations.get(generation));
        } catch (FileNotFoundException e) {
            return fitnessFunction.evaluate(aNeighbor, generation);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Double> maxFitnessValues = new ArrayList<>();
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);
        File[] files = new File(pathToTheExperiment).listFiles();
        List<String> directories = new ArrayList<>();
        GeneralMethods.showFiles(files, directories);

        for (String aPath : directories) {
            List<Double> fitnessValues = new ArrayList<>();
            List<SimpleMaterial> mutationNeighbors = getMutatedNeighbors(getLastGenerationPhenotype(aPath));
            for (SimpleMaterial aNeighbor : mutationNeighbors) {
                fitnessValues.add(getMutatedNeighbourFitness(aPath, aNeighbor, 2000));
            }
            maxFitnessValues.add(Collections.max(fitnessValues));
            System.out.println("Original: " + fitnessValues.get(0) + " max: " + Collections.max(fitnessValues));
        }
        System.out.println(maxFitnessValues);
    }
}
