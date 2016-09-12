package experiment2;

import ga.collections.Population;
import ga.components.chromosome.SimpleDiploid;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMappings.DominanceMapping;
import ga.operations.initializers.Initializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 8/09/16.
 */
public class NgWongInitializer implements Initializer<SimpleDiploid> {

    private int size;
    private int length;
    private NgWongGeneFactory factory = new NgWongGeneFactory();

    public NgWongInitializer(final int length, final int size) {
        setSize(size);
        filter(length, "Length");
        this.length = length;
    }

    private void filter(final int size, String errStr) {
        if (size < 1)
            throw new IllegalArgumentException(errStr + " must be at least 1.");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        filter(size, "Population size");
        this.size = size;
    }

    @Override
    public Population<SimpleDiploid> initialize() {
        DominanceMapping<SimpleDNA, SimpleDNA> mapping = new NgWongSchemeMapping();
        Population<SimpleDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            List<NgWongSchemeGene> genes1 = new ArrayList<>(length);
            List<NgWongSchemeGene> genes2 = new ArrayList<>(length);
            for (int j = 0; j < length; j++) {
                genes1.add(factory.generateGene());
                genes2.add(factory.generateGene());
            }
            population.addChildChromosome(new SimpleDiploid(new SimpleDNA(genes1),
                                                                new SimpleDNA(genes2),
                                                                mapping));
        }
        population.nextGeneration();
        return population;
    }

    /*
    private NgWongSchemeGene generateGene() {
        final double R = .25;
        double r = Math.random();
        for (int i = 0; i < chars.length; i++) {
            if (r < R)
                return new NgWongSchemeGene(chars[i]);
            r -= R;
        }
        return new NgWongSchemeGene('i');
    }*/
}
