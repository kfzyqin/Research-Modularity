package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.others.GeneralMethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsAllCombinationsAsymmetric extends GRNFitnessFunctionMultipleTargetsAllCombinations {


    private GRNFitnessFunctionMultipleTargetsAsymmetric grnFitnessFunctionMultipleTargetsAsymmetric;

    public GRNFitnessFunctionMultipleTargetsAllCombinationsAsymmetric(
            int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, perturbationSizes);
        this.grnFitnessFunctionMultipleTargetsAsymmetric = new GRNFitnessFunctionMultipleTargetsAsymmetric(targets,
                maxCycle, perturbations, perturbationRate, stride);
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinationsAsymmetric(
            int[][] targets, int maxCycle, double perturbationRate,
            List<Integer> thresholdOfAddingTarget, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes);
        this.perturbationSizes = perturbationSizes;
        this.grnFitnessFunctionMultipleTargetsAsymmetric = new GRNFitnessFunctionMultipleTargetsAsymmetric(targets,
                maxCycle, perturbations, perturbationRate, stride);

    }

    @Override
    protected double getHammingDistance(DataGene[] attractor, int[] target) {
        return grnFitnessFunctionMultipleTargetsAsymmetric.getHammingDistance(attractor, target);
    }
}
