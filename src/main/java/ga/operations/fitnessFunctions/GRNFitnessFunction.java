package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 22/6/17.
 */
public abstract class GRNFitnessFunction<M extends Material> implements FitnessFunction<M> {
    protected final int maxCycle;
    protected int perturbations;
    protected final double perturbationRate;

    public GRNFitnessFunction(final int maxCycle, int perturbations, final double perturbationRate) {
        this.maxCycle = maxCycle;
        this.perturbations = perturbations;
        this.perturbationRate = perturbationRate;
    }

    public GRNFitnessFunction(final int maxCycle, final double perturbationRate) {
        this.maxCycle = maxCycle;
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

    /**
     * Update the state of the perturbed target by the GRN, details on Page 2, Soto's paper.
     * Gene i is exerted by gene j.
     * @param currentState the current state of the perturbed target
     * @param phenotype the GRN
     * @return the next state of the perturbed target after matrix multiplication with the GRN
     */
    public DataGene[] updateState(DataGene[] currentState, M phenotype) {
        DataGene[] updatedState = new DataGene[currentState.length];
        updatedState = this.initializeDataGeneArray(updatedState);
        for (int i=0; i<currentState.length; i++) {
            double influence = 0;
            for (int j=0; j<currentState.length; j++) {
                int aNewInfluence = (int)
                        (phenotype.getGene(i + j*updatedState.length)).getValue() * currentState[j].getValue();
                influence += aNewInfluence;
            }
            updatedState[i].setValue(this.checkActivationOrRepression(influence));
        }
        return updatedState;
    }

    protected DataGene[] initializeDataGeneArray(DataGene[] dataGenes) {
        DataGene[] emptyDataGeneArray = new DataGene[dataGenes.length];
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
//            if (i % 2 == 0) {
//                int[] perturbingPositions =
//                        ThreadLocalRandom.current().ints(0, target.length).distinct().limit(2).toArray();
//                for (int perturbingPosition : perturbingPositions) {
//                    returnables[i][perturbingPosition].flip();
//                }
//            } else {
//                int[] perturbingPositions =
//                        ThreadLocalRandom.current().ints(0, target.length).distinct().limit(1).toArray();
//                for (int perturbingPosition : perturbingPositions) {
//                    returnables[i][perturbingPosition].flip();
//                }
//            }
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

    protected List<Integer> convertIntArrayToIntegerList(int[] intArray) {
        List<Integer> rtn = new ArrayList<>(intArray.length);
        for (int e : intArray) {
            rtn.add(e);
        }
        return rtn;
    }

    protected double evaluateEdgeCost(final SimpleMaterial phenotype) {
        int edgeNumber = 0;
        for (int i=0; i<phenotype.getSize(); i++) {
            if ((int) phenotype.getGene(i).getValue() != 0) {
                edgeNumber += 1;
            }
        }
        double lowerBound = phenotype.getSize() * 0.15;
        double upperBound = phenotype.getSize() * 0.35;
        if (edgeNumber >= lowerBound && edgeNumber <= upperBound) {
            return 1 - (edgeNumber-lowerBound) / upperBound;
        } else {
            return 0.5;
        }
    }

    public int getPerturbations() {
        return perturbations;
    }
}
