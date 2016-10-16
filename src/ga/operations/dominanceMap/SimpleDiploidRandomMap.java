package ga.operations.dominanceMap;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 9/10/16.
 */
public class SimpleDiploidRandomMap implements DominanceMap<SimpleDNA, SimpleDNA> {

    private double probability;

    public SimpleDiploidRandomMap(final double probability) {
        filter(probability);
        this.probability = probability;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1) throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public DominanceMap<SimpleDNA, SimpleDNA> copy() {
        return new SimpleDiploidRandomMap(probability);
    }

    @Override
    public SimpleDNA map(@NotNull List<SimpleDNA> materials) {
        SimpleDNA dna1 = materials.get(0);
        SimpleDNA dna2 = materials.get(1);
        // SimpleDNA dna = new SimpleDNA();
        List<Gene> genes = new ArrayList<>(dna1.getSize());
        for (int i = 0; i < dna1.getSize(); i++) {
            genes.add(i, (Math.random() < probability) ? (Gene) (dna1.getGene(i).copy()) : (Gene) dna2.getGene(i).copy());
        }
        return new SimpleDNA(genes);
    }

    @Override
    public String toString() {
        return "Random mapping with probability: " + probability;
    }
}
