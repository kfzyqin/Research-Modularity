package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;

import java.util.List;

public class GRNFitnessFunctionMultipleTargetsBalanceAsymmetric extends GRNFitnessFunctionMultipleTargets {

    protected final double stride;
    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(int[][] targets, int maxCycle, int perturbations, double perturbationRate, double stride) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.stride = stride;
    }

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(int[][] targets, int maxCycle, double perturbationRate, double stride) {
        super(targets, maxCycle, perturbationRate);
        this.stride = stride;
    }

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(int[][] targets, int maxCycle, int perturbations, double perturbationRate, List<Integer> thresholdOfAddingTarget, double stride) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.stride = stride;
    }

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(int[][] targets, int maxCycle, int perturbations, double perturbationRate, List<Integer> thresholdOfAddingTarget, String outputPath, double stride) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget, outputPath);
        this.stride = stride;
    }

    @Override
    public double getHammingDistance(DataGene[] attractor, int[] target) {
        double[] weights = new double[target.length];

        if (target.length % 2 == 0) {
            int middle = target.length / 2;
            double startWeight = 1 - (this.stride / 2) - this.stride * (middle-1);
            if (startWeight < 0) {
                throw new RuntimeException("Start weight is less than 0");
            }
            for (int i=0; i<target.length; i++) {
                weights[i] = startWeight + i * this.stride;
            }
        } else {
            int middle = (target.length - 1) / 2;
            double startWeight = 1 - this.stride * middle;
            if (startWeight < 0) {
                throw new RuntimeException("Start weight is less than 0");
            }
            for (int i=0; i<target.length; i++) {
                weights[i] = startWeight + i * this.stride;
            }
        }

        double inequality = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() != target[i]) {
                inequality += weights[i];
            }
        }
        return inequality;    }
}
