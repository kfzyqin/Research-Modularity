package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;
import ga.others.GeneralMethods;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class HotspotDiploidGRNInitializer implements Initializer<SimpleHotspotDiploid> {
    protected int size;
    private final int targetLength;
    private final int grnSize;
    private final int edgeSize;
    private final int hotspotSize;

    public HotspotDiploidGRNInitializer(final int size, final int targetLength, final int edgeSize, final int hotspotSize) {
        setSize(size);
        this.targetLength = targetLength;
        grnSize = targetLength * targetLength;
        this.edgeSize = edgeSize;
        this.hotspotSize = hotspotSize;
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
    public Population<SimpleHotspotDiploid> initialize() {
        Population<SimpleHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    public Population<SimpleHotspotDiploid> initializeSameIndividuals() {
        Population<SimpleHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            Individual<SimpleHotspotDiploid> originalIndividual = GeneralMethods.individualCloneMachine(
                    true, this.targetLength * targetLength, edgeSize, hotspotSize);
            population.addCandidate(originalIndividual.copy());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleHotspotDiploid> generateIndividual() {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GeneRegulatoryNetwork dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetwork dna2 = grnFactory.generateGeneRegulatoryNetwork();
        Hotspot hotspot = new Hotspot(this.hotspotSize, grnSize);
        return new Individual<>(new SimpleHotspotDiploid(dna1, dna2, mapping, hotspot));
    }
}
