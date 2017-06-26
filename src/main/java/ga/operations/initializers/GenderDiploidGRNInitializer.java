package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.GenderDiploid;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by zhenyueqin on 15/6/17.
 */

public class GenderDiploidGRNInitializer implements Initializer<GenderDiploid> {
    protected int size; // Size of the population.
    private final int targetLength;
    private final int grnSize;
    protected final int edgeSize;

    public GenderDiploidGRNInitializer(final int size, final int targetLength, final int edgeSize) {
        setSize(size);
        this.targetLength = targetLength;
        grnSize = targetLength * targetLength;
        this.edgeSize = edgeSize;
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

    private void filter(final int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be at least one");
        }
    }

    @Override
    public Population<GenderDiploid> initialize() {
        Population<GenderDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    protected Individual<GenderDiploid> generateIndividual() {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GeneRegulatoryNetwork dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetwork dna2 = grnFactory.generateGeneRegulatoryNetwork();
        return new Individual<>(new GenderDiploid(dna1, dna2, mapping, Math.random() < 0.5));
    }
}
