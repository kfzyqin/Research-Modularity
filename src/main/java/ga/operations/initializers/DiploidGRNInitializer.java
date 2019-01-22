package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHaploid;
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
 * Created by Zhenyue Qin on 6/06/2017.
 * The Australian National University.
 */
public class DiploidGRNInitializer implements Initializer<SimpleDiploid> {
    protected int size;
    protected final int targetLength;
    protected final int grnSize;
    protected final int edgeSize;

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

    protected Individual<SimpleDiploid> generateIndividual() {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRN dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GRN dna2 = grnFactory.generateGeneRegulatoryNetwork();
        return new Individual<>(new SimpleDiploid(dna1, dna2, mapping));
    }

    public Population<SimpleDiploid> initializeModularizedPopulation(final int moduleIndex) {
        Population<SimpleDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateModularizedIndividual(moduleIndex));
        }
        population.nextGeneration();
        return population;
    }

    public Population<SimpleDiploid> initializeExistingModularizedPopulation(final int moduleIndex) throws IOException, ParseException {
        Population<SimpleDiploid> population = new Population<>(size);
        for (int i = 0; i < size*2; i=i+2) {
            ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
            GRN grn1 = GeneralMethods.getGRNFromJSON(i, "jsons/grn_100_edge_30.json");
            GRN grn2 = GeneralMethods.getGRNFromJSON(i+1, "jsons/grn_100_edge_30.json");
            Individual<SimpleDiploid> anIndividual = new Individual<>(new SimpleDiploid(grn1, grn2, mapping));
            population.addCandidate(anIndividual);
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleDiploid> generateModularizedIndividual(final int moduleIndex) {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRN grn1 = grnFactory.generateModularizedGeneRegulatoryNetwork(moduleIndex, true);
        GRN grn2 = grnFactory.generateModularizedGeneRegulatoryNetwork(moduleIndex, true);
        return new Individual<>(new SimpleDiploid(grn1, grn2, mapping));
    }
}
