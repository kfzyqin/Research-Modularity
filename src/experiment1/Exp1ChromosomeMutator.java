package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;
import ga.operations.mutator.ChromosomeMutator;

import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public class Exp1ChromosomeMutator implements ChromosomeMutator<SimpleHaploid> {

    private double prob;

    public Exp1ChromosomeMutator(final double prob) {
        if (prob < 0 || prob > 1)
            throw new IllegalArgumentException("Value out of bound");
        this.prob = prob;
    }

    public double getProbability() {
        return prob;
    }

    public void setProbability(final double prob) {
        this.prob = prob;
    }

    @Override
    public void mutate(@NotNull List<Individual<SimpleHaploid>> individuals) {
        for (Individual<SimpleHaploid> h : individuals) {
            SimpleDNA dna = h.getChromosome().getMaterialsView().get(0);
            for (int i = 0; i < dna.getSize(); i++) {
                if (!toFlip()) continue;
                Gene<Integer> gene = (Gene<Integer>) dna.getGene(i);
                gene.setValue(gene.getValue() ^ 1);
            }
        }
    }

    private boolean toFlip() {
        return prob < Math.random();
    }
}
