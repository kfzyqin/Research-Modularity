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
import java.util.List;

public class LarsonNeighborAnalyzer {
    private static final double geneMutationRate = 0.05;
    private static final int neighborSize = 10;
    private static final String pathToTheTrial = "/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/larson-with-perturbation-recording/2018-01-13-15-54-41";

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

    public static SimpleMaterial getLastGenerationPhenotype() throws IOException {
        ProcessBuilder postPB =
                new ProcessBuilder("python", "./python-tools/last_generation_phenotype_fetcher.py",
                pathToTheTrial);
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

        for (SimpleMaterial aMaterial : mutatedNeighbors) {
            mutator.mutateAGRN(aMaterial);
        }
        return mutatedNeighbors;
    }

    public static Double getMutatedNeighbourFitness(SimpleMaterial aNeighbor, int generation) throws IOException, ClassNotFoundException {
        int[][] targets = {target1, target2};
        GRNFitnessFunctionMultipleTargets fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);
        String fileName= pathToTheTrial + "/" + "Haploid-GRN-Matrix.per";
        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        List<List<DataGene[][]>> perturbations = (List<List<DataGene[][]>>) ois.readObject();
        ois.close();
        return fitnessFunction.evaluate(aNeighbor, generation, perturbations.get(generation));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);
        List<SimpleMaterial> mutationNeighbors = getMutatedNeighbors(getLastGenerationPhenotype());
        for (SimpleMaterial aNeighbor : mutationNeighbors) {
            System.out.println(getMutatedNeighbourFitness(aNeighbor, 2000));
        }
    }
}
