package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;

import java.util.*;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsAllCombinations extends GRNFitnessFunctionMultipleTargets {
    protected int[] perturbationSizes;
    protected Map<int[], DataGene[][]> staticPerturbations = new HashMap<>();

    public GRNFitnessFunctionMultipleTargetsAllCombinations(
            int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes) {
        super(targets, maxCycle, perturbationRate);
        this.perturbationSizes = perturbationSizes;
        int allSetSize = 0;

        for (int aPerturbationSize : perturbationSizes) {
            allSetSize += GeneralMethods.getCombinationNumber(targets[0].length, aPerturbationSize);
        }

        this.perturbations = allSetSize;
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinations(
            int[][] targets, int maxCycle, double perturbationRate,
            List<Integer> thresholdOfAddingTarget, int[] perturbationSizes) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget);
        this.perturbationSizes = perturbationSizes;

        int allSetSize = 0;

        for (int aPerturbationSize : perturbationSizes) {
            allSetSize += GeneralMethods.getCombinationNumber(targets[0].length, aPerturbationSize);
        }

        this.perturbations = allSetSize;
    }

    public DataGene[][] generateInitialAttractors(int setSize, double probability, int[] target) {
        if (!this.staticPerturbations.containsKey(target)) {
            DataGene[][] returnables = new DataGene[setSize][target.length];

            Integer[] indices = new Integer[target.length];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = i;
            }

            for (int i = 0; i < setSize; i++) {
                for (int j = 0; j < target.length; j++) {
                    returnables[i][j] = new DataGene(target[j]);
                }
            }

            int overallIndex = 0;
            for (int aPerturbationSize : perturbationSizes) {
                List<List<Integer>> combinations = GeneralMethods.getCombination(indices, aPerturbationSize);
                for (List<Integer> combination : combinations) {
                    for (Integer aPosition : combination) {
                        returnables[overallIndex][aPosition].flip();
                    }
                    overallIndex += 1;
                }
            }
            this.staticPerturbations.put(target, returnables.clone());
            return returnables;
        } else {
            return this.staticPerturbations.get(target).clone();
        }
    }
}
