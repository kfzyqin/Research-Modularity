package examples.experiment4;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNFitnessFunction implements FitnessFunction<SimpleMaterial>{

    private final int[] target;
    private final int maxCycle;
    private final int perturbations;

    public GRNFitnessFunction(final int[] target, final int maxCycle, int perturbations) {
        this.target = target;
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
    }

    public boolean hasNotAttainedAttractor(final DataGene[] currentState, final DataGene[] updatedState) {
        int differenceCounts = 0;
        for (int i=0; i<currentState.length; i++) {
            if (currentState[i].getValue() != updatedState[i].getValue()) {
                differenceCounts += 1;
            }
        }
        return differenceCounts != 0;
    }

    public int checkActivationOrRepression(double influence) {
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
        DataGene[] temp = dataGenes.clone();
        for (int i=0; i<temp.length; i++) {
            temp[i] = new DataGene();
        }
        return temp;
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        DataGene[][] startAttractors = this.generateInitialAttractors(this.perturbations, 0.15);
        double fitnessValues = 0;
        for (int attractorIndex=0; attractorIndex<startAttractors.length; attractorIndex++) {
            DataGene[] currentAttractor = startAttractors[attractorIndex];
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            }
            while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                int hammingDistance = this.getHammingDistance(currentAttractor);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) this.target.length))), 5);
                fitnessValues += thisFitness;
            } else {
                fitnessValues += 0;
            }
        }
        double arithmeticMean = fitnessValues / this.perturbations;
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

    private DataGene[][] generateInitialAttractors(int setSize, double prob) {
        /*
        Generate a random set of perturbations of the target set for evaluation.
         */
        DataGene[][] returnables = new DataGene[setSize][this.target.length];
        for (int i=0; i<setSize; i++) {
            for (int j=0; j<this.target.length; j++) {
                returnables[i][j] = new DataGene(this.target[j]);
                if (Math.random() < prob) {
                    returnables[i][j].setRandomValue();
                }
            }
        }
        return returnables;
    }

    private int getHammingDistance(DataGene[] attractor) {
        /*
        TODO: make this cooler!
         */
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
