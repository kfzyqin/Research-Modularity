package experiment2;

import ga.collections.Population;
import ga.components.chromosome.SequentialDiploid;
import ga.components.materials.DNAStrand;
import ga.operations.dominanceMappings.DominanceMapping;
import ga.operations.initializers.Initializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 11/09/16.
 */
public class RyanAdditiveInitializer implements Initializer<SequentialDiploid> {

    private static final char[] chars = {'A','B','C','D'};
    private int size;
    private int length;

    public RyanAdditiveInitializer(final int length, final int size) {
        filter(length, "Length must be at least 1.");
        setSize(size);
        this.length = length;
    }

    private void filter(final int num, String errStr) {
        if (num < 1) throw new IllegalArgumentException(errStr);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        filter(size, "Population size must be at least 1.");
        this.size = size;
    }

    @Override
    public Population<SequentialDiploid> initialize() {
        Population<SequentialDiploid> population = new Population<>(size);
        DominanceMapping mapping = new RyanAdditiveScheme();
        for (int i = 0; i < size; i++) {
            List<RyanAdditiveSchemeGene> genes1 = new ArrayList<>(length);
            List<RyanAdditiveSchemeGene> genes2 = new ArrayList<>(length);
            for (int j = 0; j < length; j++) {
                genes1.add(generateGene());
                genes2.add(generateGene());
            }
            population.addChildChromosome(new SequentialDiploid(new DNAStrand(genes1),
                                                                new DNAStrand(genes2),
                                                                mapping));
        }
        population.nextGeneration();
        return population;
    }

    private RyanAdditiveSchemeGene generateGene() {
        final double R = .25;
        double r = Math.random();
        for (int i = 0; i < chars.length; i++) {
            if (r < R)
                return new RyanAdditiveSchemeGene(chars[i]);
            r -= R;
        }
        return new RyanAdditiveSchemeGene('D');
    }
}
