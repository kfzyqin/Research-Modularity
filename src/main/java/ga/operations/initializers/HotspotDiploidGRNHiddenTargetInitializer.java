package ga.operations.initializers;

import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GRNFactoryWithHiddenTargets;
import ga.components.materials.GeneRegulatoryNetworkWithHiddenTargets;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by Zhenyue Qin (秦震岳) on 2/7/17.
 * The Australian National University.
 */
public class HotspotDiploidGRNHiddenTargetInitializer extends HotspotDiploidGRNInitializer {
    public HotspotDiploidGRNHiddenTargetInitializer(int size, int targetLength, int edgeSize, int hotspotSize) {
        super(size, targetLength, edgeSize, hotspotSize);
    }

    @Override
    /*
     * Todo: this hotspot is different from others, as this uses David's approach
     */
    protected Individual<SimpleHotspotDiploid> generateIndividual() {
        GRNFactoryWithHiddenTargets grnFactory = new GRNFactoryWithHiddenTargets(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GeneRegulatoryNetworkWithHiddenTargets dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetworkWithHiddenTargets dna2 = grnFactory.generateGeneRegulatoryNetwork();
        Hotspot hotspot = new Hotspot(grnSize);
        return new Individual<>(new SimpleHotspotDiploid(dna1, dna2, mapping, hotspot));
    }
}
