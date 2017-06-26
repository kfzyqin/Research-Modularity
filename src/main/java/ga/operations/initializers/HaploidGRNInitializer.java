package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.materials.GRNFactoryNoHiddenTarget;

/**
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class HaploidGRNInitializer implements Initializer<SimpleHaploid> {
    private int size;
    private final int targetLength;
    private final int edgeSize;

    public HaploidGRNInitializer(final int size, final int targetLength, final int edgeSize) {
        this.size = size;
        setSize(size);
        this.targetLength = targetLength;
        this.edgeSize = edgeSize;
    }

    private void filter(final int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be at least one");
        }
    }

    private void probabilityFilter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        filter(size);
        this.size = size;
    }

    @Override
    public Population<SimpleHaploid> initialize() {
        Population<SimpleHaploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleHaploid> generateIndividual() {
        GRNFactoryNoHiddenTarget grnFactor = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        return new Individual<>(new SimpleHaploid(grnFactor.generateGeneRegulatoryNetwork()));
    }
}
