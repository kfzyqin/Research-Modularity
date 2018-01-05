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

    public GRNFitnessFunctionMultipleTargetsAllCombinationsAsymmetric(
            int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes) {
        super(targets, maxCycle, perturbationRate, perturbationSizes);
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinationsAsymmetric(
            int[][] targets, int maxCycle, double perturbationRate,
            List<Integer> thresholdOfAddingTarget, int[] perturbationSizes) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes);
        this.perturbationSizes = perturbationSizes;

    }

    @Override
    protected double getHammingDistance(DataGene[] attractor, int[] target) {
        double[] weights = new double[target.length];

        if (target.length % 2 == 0) {
            int middle = target.length / 2;
            double startWeight = 0.9 - 0.2 * (middle - 1);
            for (int i=0; i<target.length; i++) {
                weights[i] = startWeight + i * 0.2;
            }
        } else {
            int middle = (target.length - 1) / 2;
            double startWeight = 1 - 0.2 * middle;
            for (int i=0; i<target.length; i++) {
                weights[i] = startWeight + i * 0.2;
            }
        }

        double inequality = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() != target[i]) {
                inequality += weights[i];
            }
        }
        return inequality;
    }
}
