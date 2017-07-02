package ga.operations.mutators;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Mutates the specified gene at index i according to the rule specified in page 9 of the original paper.
         */
        for (Individual<T> individual : individuals) {
            for (Object object : individual.getChromosome().getMaterialsView()) {
                SimpleMaterial material = (SimpleMaterial) object;
                int nodeNumber = (int) Math.sqrt(material.getSize()); // number of nodes in the network.
                int regulatorNumber = 0; // number of regulators for this gene.

                for (int i = 0; i < nodeNumber; i++) {
                    if (Math.random() < this.prob) {
                        continue;
                    }
                    for (int j = 0; j < nodeNumber; j++) {
                        // if the mutated node is regulated by j.
                        if ((int) material.getGene(j * nodeNumber + i).getValue() != 0) {
                            regulatorNumber += 1;
                        }

                        double probabilityToLoseInteraction = (4.0 * regulatorNumber) / (4.0 * regulatorNumber + (nodeNumber - regulatorNumber));

                        if (Math.random() <= probabilityToLoseInteraction) { // lose an interaction
                            List<Integer> interactions = new ArrayList<>();
                            for (int edgeIdx = 0; edgeIdx < nodeNumber; edgeIdx++) {
                                if ((int) material.getGene(j * nodeNumber + i).getValue() != 0) {
                                    interactions.add(edgeIdx);
                                }
                            }
                            if (interactions.size() > 0) {
                                int toRemove = this.generateAnInteger(interactions.size());
                                material.getGene(toRemove * nodeNumber + i).setValue(0);
                            }
                        } else {
                            List<Integer> nonInteractions = new ArrayList<>();
                            for (int edgeIdx = 0; edgeIdx < nodeNumber; edgeIdx++) {
                                if ((int) material.getGene(edgeIdx * nodeNumber + i).getValue() == 0) {
                                    nonInteractions.add(edgeIdx);
                                }
                            }
                            if (nonInteractions.size() > 0) {
                                int toAdd = this.generateAnInteger(nonInteractions.size());
                                if (this.flipACoin()) {
                                    material.getGene(toAdd * nodeNumber + i).setValue(1);
                                } else {
                                    material.getGene(toAdd * nodeNumber + i).setValue(-1);
                                }
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

    private int generateAnInteger(int upBound) {
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
