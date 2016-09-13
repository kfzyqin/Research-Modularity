package ga.operations.recombiners;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.GeneticMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is a simple implementation for haploid N-point recombination for simple haploids.
 * The match probability determines the likelihood of choosing combination over the other in the pairing part.
 * The gene value swapping is performed after chromosome pairing.
 *
 * @author Siu Kei Muk (David)
 * @since 8/09/16.
 */
public class SimpleHaploidNPRecombiner implements Recombiner<SimpleHaploid> {

    private final int point;

    public SimpleHaploidNPRecombiner(final int point) {
        filter(point);
        this.point = point;
    }

    private void filter(final int point) {
        if (point < 1)
            throw new IllegalArgumentException("Invalid input values detected.");
    }

    @Override
    public List<SimpleHaploid> recombine(@NotNull List<SimpleHaploid> mates) {
        SimpleHaploid hap1 = mates.get(0).copy();
        SimpleHaploid hap2 = mates.get(1).copy();
        final int length = hap1.getLength();
        if (length-1 < point)
            throw new IllegalArgumentException("Length must be larger than crossover points.");
        GeneticMaterial material1 = hap1.getMaterialsView().get(0);
        GeneticMaterial material2 = hap1.getMaterialsView().get(1);
        List<Integer> crossoverPoints = generateCrossoverPoints(length);
        int index = 0;
        int crossIndex = crossoverPoints.get(0);
        boolean swap = false;

        for (int i = 0; i < length; i++){
            if (i == crossIndex) {
                swap = !swap;
                index++;
                crossIndex = (index < point) ? crossoverPoints.get(index) : length;
            }
            if (swap) {
                Gene gene1 = material1.getGene(i);
                Gene gene2 = material2.getGene(i);
                Object value1 = gene1.getValue();
                gene1.setValue(gene2.getValue());
                gene2.setValue(value1);
            }
        }

        List<SimpleHaploid> children = new ArrayList<>(2);
        children.add(hap1);
        children.add(hap2);
        return children;
    }

    private List<Integer> generateCrossoverPoints(final int length) {
        List<Integer> indices = new ArrayList<>(point);
        while (indices.size() < point) {
            final int index = ThreadLocalRandom.current().nextInt(1,length);
            if (!indices.contains(index))
                indices.add(index);
        }
        Collections.sort(indices);
        return indices;
    }
}
