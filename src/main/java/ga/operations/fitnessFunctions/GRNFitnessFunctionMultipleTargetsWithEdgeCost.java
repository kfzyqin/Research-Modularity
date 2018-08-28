package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

import java.util.*;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class GRNFitnessFunctionMultipleTargetsWithEdgeCost extends GRNFitnessFunctionMultipleTargets {
    protected double alpha = 0.5;

    public GRNFitnessFunctionMultipleTargetsWithEdgeCost(
            int[][] targets, int maxCycle, int perturbations, double perturbationRate, double alpha) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.alpha = alpha;
    }

    public GRNFitnessFunctionMultipleTargetsWithEdgeCost(
            int[][] targets, int maxCycle, int perturbations, double perturbationRate,
            List<Integer> thresholdOfAddingTarget, double alpha) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.alpha = alpha;
    }

    @Override
    public double evaluate(final SimpleMaterial phenotype, final int generation) {
        List<Integer> currentTargetIndices = this.getCurrentTargetIndices(generation);
        double fitnessValue = 0;
        for (Integer targetIndex : currentTargetIndices) {
            int[] aTarget = this.targets[targetIndex];
            DataGene[][] startAttractors = this.generateInitialAttractors(perturbations, perturbationRate, aTarget);
            fitnessValue += this.evaluateOneTarget(phenotype, aTarget, startAttractors);
        }
        double robustness = (fitnessValue / currentTargetIndices.size());
//        double modifiedRobustness;
//        if (robustness >= 0.8) {
//            modifiedRobustness = (robustness-0.8) / 0.15;
//        } else {
//            modifiedRobustness = robustness;
//        }

        double edgeCost = evaluateEdgeCost(phenotype);
        return 1.0 / (alpha*(1.0/robustness) + (1-alpha)*(1.0/edgeCost));
    }
}
