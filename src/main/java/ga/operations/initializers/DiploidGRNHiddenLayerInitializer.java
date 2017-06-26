package ga.operations.initializers;

import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.*;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by zhenyueqin on 25/6/17.
 */
public class DiploidGRNHiddenLayerInitializer extends DiploidGRNInitializer {

    public DiploidGRNHiddenLayerInitializer(final int size, final int targetLength,
                                            final int edgeSize) {
        super(size, targetLength, edgeSize);
    }

    @Override
    protected Individual<SimpleDiploid> generateIndividual() {
        GRNFactoryWithHiddenTargets grnFactory = new GRNFactoryWithHiddenTargets(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GeneRegulatoryNetworkHiddenTargets dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetworkHiddenTargets dna2 = grnFactory.generateGeneRegulatoryNetwork();
        return new Individual<>(new SimpleDiploid(dna1, dna2, mapping));
    }
}
