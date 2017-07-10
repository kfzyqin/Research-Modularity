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

    @Override
    public void mutate(List<Individual<T>> individuals) {
        //todo: double check this.
//        for (int i = 0; i < individuals.size(); i++) {
//            Individual<T> individual = individuals.get(i);
//            for (Object object :  individual.getChromosome().getMaterialsView()) {
//                Material material = (Material) object;
//                for (int j = 0; j < material.getSize(); j++) {
//                    flipAllele((EdgeGene) material.getGene(j));
//                }
//            }
//        }

        /*
        Mutates the specified gene at index i according to the rule specified in page 8 of the original paper.
         */
        for (Individual<T> individual : individuals) {
            for (Object object : individual.getChromosome().getMaterialsView()) {
                SimpleMaterial material = (SimpleMaterial) object;
                int targetNumber = (int) Math.sqrt(material.getSize()); // number of nodes in the network.

                for (int i = 0; i < targetNumber; i++) {
                    int regulatorNumber = 0; // number of regulators for this gene.

                    if (Math.random() > this.prob) {
                        continue;
                    }
                    for (int j=0; j<targetNumber; j++) {
                        // if the mutated node is regulated by j.
                        if ((int) material.getGene(j * targetNumber + i).getValue() != 0) {
                            regulatorNumber += 1;
                        }
                    }

                    double probabilityToLoseInteraction = (4.0 * regulatorNumber) / (4.0 * regulatorNumber + (targetNumber - regulatorNumber));

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
                    } else {
                        List<Integer> nonInteractions = new ArrayList<>();
                        for (int edgeIdx = 0; edgeIdx < targetNumber; edgeIdx++) {
                            if ((int) material.getGene(edgeIdx * targetNumber + i).getValue() == 0) {
                                nonInteractions.add(edgeIdx);
                            }
                        }
                        //todo: there might be a bug here
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

    private void flipAllele(EdgeGene gene) {
        if (Math.random() < prob) {
            if (gene.getValue() == -1) {
                gene.setValue(Math.random() < 0.5 ? 0 : 1);
            } else if (gene.getValue() == 0) {
                gene.setValue(Math.random() < 0.5 ? -1 : 1);
            } else {
                gene.setValue(Math.random() < 0.5 ? -1 : 0);
            }
        }
    }
}
