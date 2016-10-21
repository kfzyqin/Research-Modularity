package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMap.DominanceMap;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of a simple random reproducers for simple diploids.
 * The match probability determines the likelihood of choosing combination over the other in the pairing part.
 * The gene value swapping is performed after chromosomes pairing.
 *
 * @author Siu Kei Muk (David)
 * @since 8/09/16.
 */
public class SimpleDiploidReproducer implements Reproducer<SimpleDiploid> {

    private double swapProbability = 0.5;
    private double matchProbability = 0.5;
    private boolean swap = false;

    public SimpleDiploidReproducer() {
    }

    public SimpleDiploidReproducer(final double matchProbability) {
        setMatchProbability(matchProbability);
    }

    public SimpleDiploidReproducer(final double swapProbability, final boolean swap) {
        setSwapProbability(swapProbability);
        setSwap(swap);
    }

    public SimpleDiploidReproducer(final double swapProbability, final double matchProbability, final boolean swap) {
        setMatchProbability(matchProbability);
        setSwapProbability(swapProbability);
        this.swap = swap;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public List<SimpleDiploid> reproduce(@NotNull final List<SimpleDiploid> mates) {
        SimpleDiploid parent1 = mates.get(0);
        SimpleDiploid parent2 = mates.get(1);
        SimpleDNA dna1_1 = parent1.getMaterialsView().get(0).copy();
        SimpleDNA dna1_2 = parent2.getMaterialsView().get(0).copy();
        SimpleDNA dna2_1 = parent1.getMaterialsView().get(1).copy();
        SimpleDNA dna2_2 = parent2.getMaterialsView().get(1).copy();
        DominanceMap mapping1 = parent1.getMapping();
        DominanceMap mapping2 = parent2.getMapping();

        if (Math.random() > matchProbability) {
            SimpleDNA tmp = dna1_2;
            dna1_2 = dna2_2;
            dna2_2 = tmp;
        }

        if (Math.random() > matchProbability) {
            DominanceMap tmp = mapping1;
            mapping1 = mapping2;
            mapping2 = tmp;
        }

        if (swap) {
            swap(dna1_1, dna1_2);
            swap(dna2_1, dna2_2);
        }

        List<SimpleDiploid> children = new ArrayList<>(2);
        children.add(new SimpleDiploid(dna1_1, dna1_2, mapping1));
        children.add(new SimpleDiploid(dna2_1, dna2_2, mapping2));
        return children;
    }

    private void swap(SimpleDNA dna1, SimpleDNA dna2) {
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
