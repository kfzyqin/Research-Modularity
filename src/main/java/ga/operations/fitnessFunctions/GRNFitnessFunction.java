package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;

/**
 * Created by zhenyueqin on 22/6/17.
 */
public abstract class GRNFitnessFunction<M extends Material> implements FitnessFunction<M> {
    protected final int maxCycle;
    protected final int perturbations;
    protected final double perturbationRate;

    public GRNFitnessFunction(final int maxCycle, int perturbations, final double perturbationRate) {
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        this.perturbationRate = perturbationRate;
    }

    protected boolean hasNotAttainedAttractor(final DataGene[] currentState, final DataGene[] updatedState) {
        int differenceCounts = 0;
        for (int i=0; i<currentState.length; i++) {
            if (currentState[i].getValue() != updatedState[i].getValue()) {
                differenceCounts += 1;
            }
        }
        return differenceCounts != 0;
    }

    protected int checkActivationOrRepression(double influence) {
        if (influence > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    protected DataGene[] updateState(DataGene[] currentState, M phenotype, final int[] target) {
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

    protected DataGene[] initializeDataGeneArray(DataGene[] dataGenes) {
        DataGene[] emptyDataGeneArray = dataGenes.clone();
        for (int i=0; i<emptyDataGeneArray.length; i++) {
            emptyDataGeneArray[i] = new DataGene();
        }
        return emptyDataGeneArray;
    }

    protected DataGene[][] generateInitialAttractors(int setSize, double probability, int[] target) {
        /*
        Generate a random set of perturbations of the targets set for evaluation.
         */
        DataGene[][] returnables = new DataGene[setSize][target.length];
        for (int i=0; i<setSize; i++) {
            for (int j = 0; j<target.length; j++) {
                returnables[i][j] = new DataGene(target[j]);
                if (Math.random() < probability) {
                    returnables[i][j].flip();
                }
            }
        }
        return returnables;
    }

    protected int getHammingDistance(DataGene[] attractor, int[] target) {
        int count = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() == target[i]) {
                count += 1;
            }
        }
        return attractor.length - count;
    }
}
