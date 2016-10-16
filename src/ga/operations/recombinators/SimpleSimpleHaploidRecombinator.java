package ga.operations.recombinators;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.GeneticMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple implementation for simple haploid random recombinators for simple haploids.
 * The match probability determines the likelihood of choosing combination over the other in the pairing part.
 * The gene value swapping is performed after chromosomes pairing.
 *
 * @author Siu Kei Muk (David)
 * @since 8/09/16.
 */
public class SimpleSimpleHaploidRecombinator implements Recombinator<SimpleHaploid> {

    private double probability;

    public SimpleSimpleHaploidRecombinator(final double probability) {
        filter(probability);
        this.probability = probability;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public List<SimpleHaploid> recombine(@NotNull final List<SimpleHaploid> mates) {
        if (mates.size() != 2)
            throw new IllegalArgumentException("The input must consists of exactly 2 chromosomes.");
        SimpleHaploid hap1 = mates.get(0).copy();
        SimpleHaploid hap2 = mates.get(1).copy();
        GeneticMaterial material1 = hap1.getMaterialsView().get(0);
        GeneticMaterial material2 = hap2.getMaterialsView().get(1);
        for (int i = 0; i < hap1.getLength(); i++) {
            if (Math.random() < probability) {
                Gene gene1 = material1.getGene(i);
                Gene gene2 = material2.getGene(i);
                Object value1 = gene1.getValue();
                Object value2 = gene2.getValue();
                gene1.setValue(value2);
                gene2.setValue(value1);
            }
        }
        List<SimpleHaploid> children = new ArrayList<>(2);
        children.add(hap1);
        children.add(hap2);
        return children;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        filter(probability);
        this.probability = probability;
    }
}
