package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNFitnessFunctionWithSingleTarget implements FitnessFunction<SimpleMaterial>{

    private final int[] target;
    private final int maxCycle;
    private final int perturbations;
    private final double perturbationRate;

    public GRNFitnessFunctionWithSingleTarget(final int[] target, final int maxCycle, int perturbations, final double perturbationRate) {
        this.target = target;
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        this.perturbationRate = perturbationRate;
    }

    private boolean hasNotAttainedAttractor(final DataGene[] currentState, final DataGene[] updatedState) {
        int differenceCounts = 0;
        for (int i=0; i<currentState.length; i++) {
            if (currentState[i].getValue() != updatedState[i].getValue()) {
                differenceCounts += 1;
            }
        }
        return differenceCounts != 0;
    }

    private int checkActivationOrRepression(double influence) {
        if (influence > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    private DataGene[] updateState(DataGene[] currentState, SimpleMaterial phenotype) {
        DataGene[] updatedState = new DataGene[currentState.length];
        updatedState = this.initializeDataGeneArray(updatedState);
        for (int i=0; i<currentState.length; i++) {
            double influence = 0;
            for (int j=0; j<currentState.length; j++) {
                influence += (int) (phenotype.getGene(i*target.length + j)).getValue() * currentState[j].getValue();
            }
            updatedState[i].setValue(this.checkActivationOrRepression(influence));
        }
        return updatedState;
    }

    private DataGene[] initializeDataGeneArray(DataGene[] dataGenes) {
        DataGene[] emptyDataGeneArray = dataGenes.clone();
        for (int i=0; i<emptyDataGeneArray.length; i++) {
            emptyDataGeneArray[i] = new DataGene();
        }
        return emptyDataGeneArray;
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        DataGene[][] startAttractors = this.generateInitialAttractors(this.perturbations, this.perturbationRate);
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
                int hammingDistance = this.getHammingDistance(currentAttractor);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) this.target.length))), 5);
                fitnessValue += thisFitness;
            } else {
                fitnessValue += 0;
            }
        }
        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return fitnessValue;

//        int ones = 0;
//        for (int i=0; i<phenotype.getSize(); i++) {
//            int aPosition = (Integer) phenotype.getGene(i).getValue();
//            if (aPosition == 1) {
//                ones += 1;
//            }
//        }
//        return ones;
    }

    private DataGene[][] generateInitialAttractors(int setSize, double prob) {
        /*
        Generate a random set of perturbations of the target set for evaluation.
         */
        DataGene[][] returnables = new DataGene[setSize][this.target.length];
        for (int i=0; i<setSize; i++) {
            for (int j=0; j<this.target.length; j++) {
                returnables[i][j] = new DataGene(this.target[j]);
                if (Math.random() < prob) {
                    returnables[i][j].flip();
                }
            }
        }
        return returnables;
    }

    private int getHammingDistance(DataGene[] attractor) {
        int count = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() == this.target[i]) {
                count += 1;
            }
        }
        return attractor.length - count;
    }

    @Override
    public void update() {

    }
}
