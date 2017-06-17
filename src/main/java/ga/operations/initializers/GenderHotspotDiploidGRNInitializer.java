package ga.operations.initializers;

import ga.collections.Individual;
import ga.components.chromosomes.GenderDiploid;
import ga.components.chromosomes.GenderHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GeneRegulatoryNetworkFactory;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by zhenyueqin on 15/6/17.
 */
public class GenderHotspotDiploidGRNInitializer extends GenderDiploidGRNInitializer {
    private final int hotspotSize;
    private final double hotspotMutationRate;

    public GenderHotspotDiploidGRNInitializer(int size, int[] target, int edgeSize, double dominanceMutationRate,
                                              final int hotspotSize, final double hotspotMutationRate) {
        super(size, target, edgeSize, dominanceMutationRate);
        this.hotspotSize = hotspotSize;
        this.hotspotMutationRate = hotspotMutationRate;
    }

    @Override
    protected Individual<GenderDiploid> generateIndividual() {
        GeneRegulatoryNetworkFactory grnFactor = new GeneRegulatoryNetworkFactory(this.target, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(this.target.getSize() * this.target.getSize());
        GeneRegulatoryNetwork dna1 = grnFactor.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetwork dna2 = grnFactor.generateGeneRegulatoryNetwork();
        Hotspot hotspot = new Hotspot(this.hotspotSize, target.getSize() * target.getSize());
        return new Individual<>(new GenderHotspotDiploid(dna1, dna2, mapping, hotspot, Math.random() < 0.5));
    }
}
