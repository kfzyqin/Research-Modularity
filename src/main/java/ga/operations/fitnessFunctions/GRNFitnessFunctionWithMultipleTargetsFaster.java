package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyueqin on 22/6/17.
 */
public class GRNFitnessFunctionWithMultipleTargetsFaster extends GRNFitnessFunctionWithMultipleTargets {

    private final int perturbationCycleSize;
    private List<DataGene[][]> perturbationPool;

    public GRNFitnessFunctionWithMultipleTargetsFaster(int[][] targets, int maxCycle, int perturbations,
                                                       double perturbationRate, final int perturbationCycleSize) {
        super(targets, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionWithMultipleTargetsFaster(int[] target1, int[] target2, int maxCycle, int perturbations,
                                                       double perturbationRate, final int perturbationCycleSize) {
        super(target1, target2, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionWithMultipleTargetsFaster(int[] target1, int[] target2, int[] target3, int maxCycle,
                                                       int perturbations, double perturbationRate,
                                                       final int perturbationCycleSize) {
        super(target1, target2, target3, maxCycle, perturbations, perturbationRate);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionWithMultipleTargetsFaster(int[][] targets, int maxCycle, int perturbations,
                                                       double perturbationRate, List<Integer> thresholdOfAddingTarget,
                                                       final int perturbationCycleSize) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionWithMultipleTargetsFaster(int[] target1, int[] target2, int maxCycle, int perturbations,
                                                       double perturbationRate, List<Integer> thresholdOfAddingTarget,
                                                       final int perturbationCycleSize) {
        super(target1, target2, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }

    public GRNFitnessFunctionWithMultipleTargetsFaster(int[] target1, int[] target2, int[] target3, int maxCycle,
                                                       int perturbations, double perturbationRate,
                                                       List<Integer> thresholdOfAddingTarget,
                                                       final int perturbationCycleSize) {
        super(target1, target2, target3, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget);
        this.perturbationCycleSize = perturbationCycleSize;
        generatePerturbationPool();
    }


    private void generatePerturbationPool() {
        for (int[] target : targets) {
            perturbationPool.add(generateInitialAttractors(perturbationCycleSize, perturbationRate, target));
        }
    }

    @Override
    public double evaluate(SimpleMaterial phenotype, int generation) {
        return 0;
    }

    @Override
    public double evaluate(SimpleMaterial phenotype) {
        return 0;
    }

    @Override
    public void update() {

    }
}
