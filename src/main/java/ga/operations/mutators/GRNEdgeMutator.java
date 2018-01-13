package ga.operations.mutators;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a mutator to mutate a GRN.
 *
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNEdgeMutator<T extends Chromosome> implements Mutator<T> {
    private double prob;

    public GRNEdgeMutator(final double prob) {
        filter(prob);
        this.prob = prob;
    }

    private void filter(final double prob) {
        if (prob < 0 || prob > 1)
            throw new IllegalArgumentException("Mutation probability must be between 0 and 1.");
    }

    public double getProbability() {
        return prob;
    }

    public void setProbability(final double prob) {
        filter(prob);
        this.prob = prob;
    }

    protected void actualMutate(double probabilityToLoseInteraction, SimpleMaterial material, int targetNumber, int i) {
        if (Math.random() <= probabilityToLoseInteraction) { // lose an interaction
            List<Integer> interactions = new ArrayList<>();
            for (int edgeIdx = 0; edgeIdx < targetNumber; edgeIdx++) {
                if ((int) material.getGene(edgeIdx * targetNumber + i).getValue() != 0) {
                    interactions.add(edgeIdx);
                }
            }
            if (interactions.size() > 0) {
                int toRemove = ThreadLocalRandom.current().nextInt(interactions.size());
                material.getGene(interactions.get(toRemove) * targetNumber + i).setValue(0);
            }
        } else { // gain an interaction
            List<Integer> nonInteractions = new ArrayList<>();
            for (int edgeIdx = 0; edgeIdx < targetNumber; edgeIdx++) {
                if ((int) material.getGene(edgeIdx * targetNumber + i).getValue() == 0) {
                    nonInteractions.add(edgeIdx);
                }
            }
            if (nonInteractions.size() > 0) {
                int toAdd = this.generateAnRandomInteger(nonInteractions.size());
                if (this.flipACoin()) {
                    material.getGene(nonInteractions.get(toAdd) * targetNumber + i).setValue(1);
                } else {
                    material.getGene(nonInteractions.get(toAdd) * targetNumber + i).setValue(-1);
                }
            }
        }
    }

    public void mutateAGRN(SimpleMaterial material) {
        int targetNumber = (int) Math.sqrt(material.getSize());

        for (int i=0; i<targetNumber; i++) {
                    /* Number of regulators for gene i */
            int regulatorNumber = 0;

                    /* Does not meet the mutation rate */
            if (Math.random() > this.prob) {
                continue;
            }

                    /* Get how many genes that regulate gene i */
            for (int j=0; j<targetNumber; j++) {
                if ((int) material.getGene(j * targetNumber + i).getValue() != 0) {
                    regulatorNumber += 1;
                }
            }

                    /* Use this formula to maintain the number of edges */
            double probabilityToLoseInteraction =
                    (4.0 * regulatorNumber) / (4.0 * regulatorNumber + (targetNumber - regulatorNumber));

            actualMutate(probabilityToLoseInteraction, material, targetNumber, i);
        }
    }

    /**
     * Mutates the specified gene at index i according to the rule specified in page 8 of the original paper.
     * @param individuals individuals to be mutated
     */
    @Override
    public void mutate(List<Individual<T>> individuals) {
        for (Individual<T> individual : individuals) {
            for (Object object : individual.getChromosome().getMaterialsView()) {
                SimpleMaterial material = (SimpleMaterial) object;
                mutateAGRN(material);
            }
        }

    }

    private boolean flipACoin() {
        return 0.5 < Math.random();
    }

    private int generateAnRandomInteger(int upBound) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(upBound);
    }
}
