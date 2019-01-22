package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.hotspots.MatrixHotspot;
import ga.components.materials.GRN;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;
import ga.others.GeneralMethods;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class HotspotDiploidGRNInitializer implements Initializer<SimpleHotspotDiploid> {
    protected int size;
    final int targetLength;
    final int grnSize;
    protected final int edgeSize;
    protected final int hotspotSize;

    public HotspotDiploidGRNInitializer(
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
    public Population<SimpleHotspotDiploid> initialize() {
        Population<SimpleHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    protected Individual<SimpleHotspotDiploid> generateIndividual() {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRN dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GRN dna2 = grnFactory.generateGeneRegulatoryNetwork();
        Hotspot hotspot = new Hotspot(this.hotspotSize, grnSize);
        return new Individual<>(new SimpleHotspotDiploid(dna1, dna2, mapping, hotspot));
    }

    protected Individual<SimpleHotspotDiploid> generateIndividualWithMatrixHotspot() {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRN dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GRN dna2 = grnFactory.generateGeneRegulatoryNetwork();
        MatrixHotspot hotspot = new MatrixHotspot(this.hotspotSize, grnSize);
        return new Individual<>(new SimpleHotspotDiploid(dna1, dna2, mapping, hotspot));
    }

    public Population<SimpleHotspotDiploid> initializeWithMatrixHotspot() {
        Population<SimpleHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividualWithMatrixHotspot());
        }
        population.nextGeneration();
        return population;
    }

    public Population<SimpleHotspotDiploid> initializeModularizedPopulationWithMatrixHotspot(final int moduleIndex) {
        Population<SimpleHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateModularizedIndividualWithMatrixHotspot(moduleIndex));
        }
        population.nextGeneration();
        return population;
    }

    public Population<SimpleHotspotDiploid> initializeExistingModularizedPopulationWithMatrixHotspot(final int moduleIndex) throws IOException, ParseException {
        Population<SimpleHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size*2; i=i+2) {
            ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
            GRN grn1 = GeneralMethods.getGRNFromJSON(i, "jsons/grn_100_edge_30.json");
            GRN grn2 = GeneralMethods.getGRNFromJSON(i+1, "jsons/grn_100_edge_30.json");
            MatrixHotspot matrixHotspot = GeneralMethods.getMatrixHotspotFromJSON(i/2, "jsons/hotspot_50.json");
            Individual<SimpleHotspotDiploid> anIndividual = new Individual<>(new SimpleHotspotDiploid(grn1, grn2, mapping, matrixHotspot));
            population.addCandidate(anIndividual);
        }
        population.nextGeneration();
        return population;

    }

    protected Individual<SimpleHotspotDiploid> generateModularizedIndividualWithMatrixHotspot(final int moduleIndex) {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRN grn1 = grnFactory.generateModularizedGeneRegulatoryNetwork(moduleIndex, true);
        GRN grn2 = grnFactory.generateModularizedGeneRegulatoryNetwork(moduleIndex, true);
        MatrixHotspot matrixHotspot = new MatrixHotspot(this.hotspotSize, grnSize);
        return new Individual<>(new SimpleHotspotDiploid(grn1, grn2, mapping, matrixHotspot));
    }
}
