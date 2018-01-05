package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

import java.util.*;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class GRNFitnessFunctionMultipleTargets extends GRNFitnessFunction<SimpleMaterial>
        implements FitnessFunctionMultipleTargets<SimpleMaterial> {
    protected final int[][] targets;
    protected Map<List<Integer>, Map<SimpleMaterial, Double>> targetPhenotypeFitnessMap = new HashMap<>();

    protected final List<Integer> thresholdOfAddingTarget;

    public GRNFitnessFunctionMultipleTargets(final int[][] targets, final int maxCycle, int perturbations, double perturbationRate) {
        super(maxCycle, perturbations, perturbationRate);
        this.targets = targets;
        thresholdOfAddingTarget = new ArrayList<>(1);
        this.thresholdOfAddingTarget.add(0);
    }

    public GRNFitnessFunctionMultipleTargets(final int[][] targets, final int maxCycle, double perturbationRate) {
        super(maxCycle, perturbationRate);
        this.targets = targets;
        thresholdOfAddingTarget = new ArrayList<>(1);
        this.thresholdOfAddingTarget.add(0);
    }

    public GRNFitnessFunctionMultipleTargets(final int[][] targets, final int maxCycle, int perturbations,
                                             double perturbationRate,
                                             final List<Integer> thresholdOfAddingTarget) {
        super(maxCycle, perturbations, perturbationRate);
        this.targets = targets;
        this.thresholdOfAddingTarget = thresholdOfAddingTarget;
        Collections.sort(this.thresholdOfAddingTarget);
        filterThresholds();
    }

    public GRNFitnessFunctionMultipleTargets(final int[][] targets, final int maxCycle,
                                             double perturbationRate,
                                             final List<Integer> thresholdOfAddingTarget) {
        super(maxCycle, perturbationRate);
        this.targets = targets;
        this.thresholdOfAddingTarget = thresholdOfAddingTarget;
        Collections.sort(this.thresholdOfAddingTarget);
        filterThresholds();
    }

    /**
     * Check whether lengths of targets are the same.
     */
    private void filterTargets() {
        if (targets.length == 1) return;
        int lastTargetLength = targets[0].length;
        for (int i = 1; i< targets.length; i++) {
            if (targets[i].length != lastTargetLength) {
                throw new IllegalArgumentException("Lengths of targets are different.");
            }
            lastTargetLength = targets[i].length;
        }
    }

    /**
     * The first threshold must be 1.
     */
    private void filterThresholds() {
        if (thresholdOfAddingTarget.get(0) != 0) {
            throw new IllegalArgumentException("The first threshold must be 1.");
        }
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        return evaluate(phenotype, 0);
    }

    @Override
    public void update() {

    }

    protected List<Integer> getCurrentTargetIndices(int generation) {
        List<Integer> currentTargetIndices = new ArrayList<>();
        for (int i=0; i<thresholdOfAddingTarget.size(); i++) {
            if (generation >= thresholdOfAddingTarget.get(i)) {
                currentTargetIndices.add(i);
            } else {
                break;
            }
        }
        return currentTargetIndices;
    }

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target) {
        DataGene[][] startAttractors = this.generateInitialAttractors(perturbations, perturbationRate, target);
        double fitnessValue = 0;
        for (DataGene[] startAttractor : startAttractors) {
            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                double hammingDistance = this.getHammingDistance(currentAttractor, target);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) target.length))), 5);
                fitnessValue += thisFitness;
            } else {
                fitnessValue += 0;
            }
        }
        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return networkFitness;

//        int ones = 0;
//        for (int i=0; i<phenotype.getSize(); i++) {
//            int aPosition = (Integer) phenotype.getGene(i).getValue();
//            if (aPosition == 1) {
//                ones += 1;
//            }
//        }
//        return ones;
    }

    @Override
    public double evaluate(final SimpleMaterial phenotype, final int generation) {
        List<Integer> currentTargetIndices = this.getCurrentTargetIndices(generation);
        double fitnessValue = 0;
        for (Integer targetIndex : currentTargetIndices) {
            int[] aTarget = this.targets[targetIndex];
            fitnessValue += this.evaluateOneTarget(phenotype, aTarget);
        }
        return fitnessValue / currentTargetIndices.size();
    }
}
