package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GeneRegulatoryNetworkFactory;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by Zhenyue Qin on 6/06/2017.
 * The Australian National University.
 */
public class DiploidGRNInitializer implements Initializer<SimpleDiploid> {
    private int size;
    private final int targetLength;
    private final int grnSize;
    private final int edgeSize;

    public DiploidGRNInitializer(final int size, final int targetLength, final int edgeSize) {
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
    public Population<SimpleDiploid> initialize() {
        Population<SimpleDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleDiploid> generateIndividual() {
        GeneRegulatoryNetworkFactory grnFactory = new GeneRegulatoryNetworkFactory(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GeneRegulatoryNetwork dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetwork dna2 = grnFactory.generateGeneRegulatoryNetwork();
        return new Individual<>(new SimpleDiploid(dna1, dna2, mapping));
    }
}
