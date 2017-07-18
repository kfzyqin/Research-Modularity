package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.chromosomes.SimpleHotspotHaploid;
import ga.components.hotspots.Hotspot;
import ga.components.hotspots.MatrixHotspot;
import ga.components.materials.GRN;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by Zhenyue Qin (秦震岳) on 15/7/17.
 * The Australian National University.
 */
public class HotspotHaploidGRNInitializer implements Initializer<SimpleHotspotHaploid> {
    protected int size;
    final int targetLength;
    final int grnSize;
    protected final int edgeSize;
    protected final int hotspotSize;

    public HotspotHaploidGRNInitializer(
            final int size, final int targetLength, final int edgeSize, final int hotspotSize) {
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
    public Population<SimpleHotspotHaploid> initialize() {
        Population<SimpleHotspotHaploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    protected Individual<SimpleHotspotHaploid> generateIndividual() {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        MatrixHotspot matrixHotspot = new MatrixHotspot(this.hotspotSize, grnSize);
        GRN grn = grnFactory.generateGeneRegulatoryNetwork();
        return new Individual<>(new SimpleHotspotHaploid(grn, matrixHotspot));
    }

    public Population<SimpleHotspotHaploid> initializeModularizedPopulation(final int moduleIndex) {
        Population<SimpleHotspotHaploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateModularizedIndividual(moduleIndex));
        }
        population.nextGeneration();
        return population;
    }

    protected Individual<SimpleHotspotHaploid> generateModularizedIndividual(final int moduleIndex) {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        MatrixHotspot matrixHotspot = new MatrixHotspot(this.hotspotSize, grnSize);
        GRN grn = grnFactory.generateModularizedGeneRegulatoryNetwork(moduleIndex);
        return new Individual<>(new SimpleHotspotHaploid(grn, matrixHotspot));
    }
}
