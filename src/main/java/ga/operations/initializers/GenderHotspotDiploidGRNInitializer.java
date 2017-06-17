package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.GenderDiploid;
import ga.components.chromosomes.GenderHotspotDiploid;
import ga.components.genes.DataGene;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GeneRegulatoryNetworkFactory;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;

/**
 * Created by zhenyueqin on 15/6/17.
 */
public class GenderHotspotDiploidGRNInitializer implements Initializer<GenderHotspotDiploid> {
    protected int size;
    protected final SimpleMaterial target;
    private final int edgeSize;
    private final int hotspotSize;

    public GenderHotspotDiploidGRNInitializer(final int size, final int[] target, final int edgeSize, final int hotspotSize) {
        setSize(size);
        ArrayList<DataGene> tempTargetList = new ArrayList<>();
        for (int aTarget : target) {
            tempTargetList.add(new DataGene(aTarget));
        }
        this.target = new SimpleMaterial(tempTargetList);
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
    public Population<GenderHotspotDiploid> initialize() {
        Population<GenderHotspotDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<GenderHotspotDiploid> generateIndividual() {
        GeneRegulatoryNetworkFactory grnFactor = new GeneRegulatoryNetworkFactory(this.target, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(this.target.getSize() * this.target.getSize());
        GeneRegulatoryNetwork dna1 = grnFactor.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetwork dna2 = grnFactor.generateGeneRegulatoryNetwork();
        Hotspot hotspot = new Hotspot(this.hotspotSize, target.getSize() * target.getSize());
        return new Individual<>(new GenderHotspotDiploid(dna1, dna2, mapping, hotspot, Math.random() < 0.5));
    }
}
