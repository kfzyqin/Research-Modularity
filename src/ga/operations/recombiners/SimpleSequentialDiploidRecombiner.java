package ga.operations.recombiners;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.SequentialDiploid;
import ga.components.genes.Gene;
import ga.components.materials.DNAStrand;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 8/09/16.
 */
public class SimpleSequentialDiploidRecombiner implements Recombiner<SequentialDiploid> {

    private double swapProbability = 0.5;
    private double matchProbability = 0.5;
    private boolean swap = false;

    public SimpleSequentialDiploidRecombiner() {
    }

    public SimpleSequentialDiploidRecombiner(final double matchProbability) {
        setMatchProbability(matchProbability);
    }

    public SimpleSequentialDiploidRecombiner(final double swapProbability, final boolean swap) {
        setSwapProbability(swapProbability);
        setSwap(swap);
    }

    public SimpleSequentialDiploidRecombiner(final double swapProbability, final double matchProbability, final boolean swap) {
        setMatchProbability(matchProbability);
        setSwapProbability(swapProbability);
        this.swap = swap;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public List<SequentialDiploid> recombine(@NotNull final List<SequentialDiploid> mates) {
        SequentialDiploid parent1 = mates.get(0);
        SequentialDiploid parent2 = mates.get(1);
        DNAStrand dna1_1 = parent1.getMaterialsView().get(0).copy();
        DNAStrand dna1_2 = parent2.getMaterialsView().get(0).copy();
        DNAStrand dna2_1 = parent1.getMaterialsView().get(1).copy();
        DNAStrand dna2_2 = parent2.getMaterialsView().get(1).copy();
        DominanceMapping mapping1 = parent1.getMapping();
        DominanceMapping mapping2 = parent2.getMapping();

        if (Math.random() > matchProbability) {
            DNAStrand tmp = dna1_2;
            dna1_2 = dna2_2;
            dna2_2 = tmp;
        }

        if (Math.random() > matchProbability) {
            DominanceMapping tmp = mapping1;
            mapping1 = mapping2;
            mapping2 = tmp;
        }

        if (swap) {
            swap(dna1_1, dna1_2);
            swap(dna2_1, dna2_2);
        }

        List<SequentialDiploid> children = new ArrayList<>(2);
        children.add(new SequentialDiploid(dna1_1, dna1_2, mapping1));
        children.add(new SequentialDiploid(dna2_1, dna2_2, mapping2));
        return children;
    }

    private void swap(DNAStrand dna1, DNAStrand dna2) {
        final int length = dna1.getSize();
        for (int i = 0; i < length; i++) {
            if (Math.random() < swapProbability) {
                Gene gene1 = dna1.getGene(i);
                Gene gene2 = dna2.getGene(i);
                Object value1 = gene1.getValue();
                gene1.setValue(gene2.getValue());
                gene2.setValue(value1);
            }
        }
    }

    public double getMatchProbability() {
        return matchProbability;
    }

    public void setMatchProbability(final double matchProbability) {
        filter(matchProbability);
        this.matchProbability = matchProbability;
    }

    public double getSwapProbability() {
        return swapProbability;
    }

    public void setSwapProbability(final double swapProbability) {
        filter(swapProbability);
        this.swapProbability = swapProbability;
    }

    public boolean isSwap() {
        return swap;
    }

    public void setSwap(boolean swap) {
        this.swap = swap;
    }
}
