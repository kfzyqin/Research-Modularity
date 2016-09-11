package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.SequentialHaploid;
import ga.components.genes.Gene;
import ga.components.materials.DNAStrand;
import ga.operations.mutators.Mutator;

import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Mutator implements Mutator<SequentialHaploid>{

    private double prob;

    public Exp1Mutator(final double prob) {
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
    public void mutate(@NotNull List<Individual<SequentialHaploid>> individuals) {
        for (Individual<SequentialHaploid> h : individuals) {
            DNAStrand dna = h.getChromosome().getMaterialsView().get(0);
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
