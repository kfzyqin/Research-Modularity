package ga.operations.mutator;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;
import ga.components.genes.BinaryGene;
import ga.components.materials.GeneticMaterial;

import java.util.List;

/**
 * This class implements a mutator operation for chromosomes with pure binary genes.
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public class BinaryGeneChromosomeMutator<T extends Chromosome> implements ChromosomeMutator<T> {

    private double prob;

    /**
     * Constructs a BinaryGeneChromosomeMutator object.
     * @param prob mutator probability
     */
    public BinaryGeneChromosomeMutator(final double prob) {
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
    public void mutate(@NotNull List<Individual<T>> individuals) {
        for (int i = 0; i < individuals.size(); i++) {
            Individual<T> individual = individuals.get(i);
            for (Object object :  individual.getChromosome().getMaterialsView()) {
                GeneticMaterial material = (GeneticMaterial) object;
                for (int j = 0; j < material.getSize(); j++) {
                    flipAllele((BinaryGene) material.getGene(j));
                }
            }
        }
    }

    private void flipAllele(BinaryGene gene) {
        if (Math.random() < prob)
            gene.setValue(gene.getValue() ^ 1);
    }

}
