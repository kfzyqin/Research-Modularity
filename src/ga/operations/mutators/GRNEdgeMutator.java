package ga.operations.mutators;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.BinaryGene;
import ga.components.genes.EdgeGene;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;

import java.util.List;

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
        for (int i = 0; i < individuals.size(); i++) {
            Individual<T> individual = individuals.get(i);
            for (Object object :  individual.getChromosome().getMaterialsView()) {
                Material material = (Material) object;
                for (int j = 0; j < material.getSize(); j++) {
                    flipAllele((EdgeGene) material.getGene(j));
                }
            }
        }
    }

    private void flipAllele(EdgeGene gene) {
        if (Math.random() < prob) {
            if (gene.getValue() == -1) {
                gene.setValue(Math.random() < 0.5 ? 0 : 1);
            } else {
                gene.setValue(Math.random() < 0.5 ? -1 : (1 - gene.getValue()));
            }
        }
    }
}
