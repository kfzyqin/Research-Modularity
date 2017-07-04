package ga.operations.initializers;

import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.*;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by zhenyueqin on 25/6/17.
 */
public class DiploidGRNHiddenTargetInitializer extends DiploidGRNInitializer {
    private final int actualTargetSize;
    private final int hiddenTargetSize;

    public DiploidGRNHiddenTargetInitializer(final int size, int actualTargetSize, int hiddenTargetSize,
                                             final int edgeSize) {
        super(size, actualTargetSize + hiddenTargetSize, edgeSize);
        this.actualTargetSize = actualTargetSize;
        this.hiddenTargetSize = hiddenTargetSize;
    }

    @Override
    protected Individual<SimpleDiploid> generateIndividual() {
        GRNFactoryWithHiddenTargets grnFactory = new GRNFactoryWithHiddenTargets(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRNWithHiddenTargets dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GRNWithHiddenTargets dna2 = grnFactory.generateGeneRegulatoryNetwork();
        return new Individual<>(new SimpleDiploid(dna1, dna2, mapping));
    }

    public int getActualTargetSize() {
        return actualTargetSize;
    }

    public int getHiddenTargetSize() {
        return hiddenTargetSize;
    }
}
