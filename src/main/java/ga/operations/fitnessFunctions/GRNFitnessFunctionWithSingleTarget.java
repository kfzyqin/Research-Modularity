package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNFitnessFunctionWithSingleTarget extends GRNFitnessFunction<SimpleMaterial> {
    private final int[] target;

    public GRNFitnessFunctionWithSingleTarget(int[] target, int maxCycle, int perturbations, double perturbationRate) {
        super(maxCycle, perturbations, perturbationRate);
        this.target = target;
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        DataGene[][] startAttractors = this.generateInitialAttractors(this.perturbations, this.perturbationRate, this.target);
        double fitnessValue = 0;
        for (DataGene[] startAttractor : startAttractors) {
            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype, this.target);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                int hammingDistance = this.getHammingDistance(currentAttractor, this.target);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) this.target.length))), 5);
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
    public void update() {

    }
}
