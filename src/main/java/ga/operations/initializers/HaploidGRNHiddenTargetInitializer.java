package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.components.materials.GRNFactoryWithHiddenTargets;
import ga.components.materials.GRNWithHiddenTargets;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class HaploidGRNHiddenTargetInitializer extends HaploidGRNInitializer {
    private final int actualTargetSize;
    private final int hiddenTargetSize;

    public HaploidGRNHiddenTargetInitializer(final int size, int actualTargetSize, int hiddenTargetSize,
                                             final int edgeSize) {
        super(size, actualTargetSize + hiddenTargetSize, edgeSize);
        this.actualTargetSize = actualTargetSize;
        this.hiddenTargetSize = hiddenTargetSize;
    }

    @Override
    protected Individual<SimpleHaploid> generateIndividual() {
        GRNFactoryWithHiddenTargets grnFactory = new GRNFactoryWithHiddenTargets(targetLength, this.edgeSize);
        return new Individual<>(new SimpleHaploid(grnFactory.generateGeneRegulatoryNetwork()));
    }
    
}
