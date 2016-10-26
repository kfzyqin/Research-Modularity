package ga.operations.expressionMaps;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 9/10/16.
 */
public class SimpleDiploidRandomMap implements ExpressionMap<SimpleMaterial, SimpleMaterial> {

    private double probability;

    public SimpleDiploidRandomMap(final double probability) {
        filter(probability);
        this.probability = probability;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1) throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public ExpressionMap<SimpleMaterial, SimpleMaterial> copy() {
        return new SimpleDiploidRandomMap(probability);
    }

    @Override
    public SimpleMaterial map(@NotNull List<SimpleMaterial> materials) {
        SimpleMaterial dna1 = materials.get(0);
        SimpleMaterial dna2 = materials.get(1);
        // SimpleMaterial dna = new SimpleMaterial();
        List<Gene> genes = new ArrayList<>(dna1.getSize());
        for (int i = 0; i < dna1.getSize(); i++) {
            genes.add(i, (Math.random() < probability) ? (Gene) (dna1.getGene(i).copy()) : (Gene) dna2.getGene(i).copy());
        }
        return new SimpleMaterial(genes);
    }

    @Override
    public String toString() {
        return "Random mapping with probability: " + probability;
    }
}
