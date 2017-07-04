package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.hotspots.MatrixHotspot;
import ga.components.materials.*;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;
import ga.others.GeneralMethods;

/**
 * Created by Zhenyue Qin (秦震岳) on 2/7/17.
 * The Australian National University.
 */
public class HotspotDiploidGRNHiddenTargetInitializer extends HotspotDiploidGRNInitializer {
    private final int actualTargetSize;
    private final int hiddenTargetSize;

    public HotspotDiploidGRNHiddenTargetInitializer(
            int size, int actualTargetSize, int hiddenTargetSize, int edgeSize, int hotspotSize) {
        super(size, actualTargetSize + hiddenTargetSize, edgeSize, hotspotSize);
        this.actualTargetSize = actualTargetSize;
        this.hiddenTargetSize = hiddenTargetSize;
    }

    @Override
    /*
     * Todo: this hotspot is different from others, as this uses David's approach
     */
    protected Individual<SimpleHotspotDiploid> generateIndividual() {
        GRNFactoryWithHiddenTargets grnFactory = new GRNFactoryWithHiddenTargets(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRNWithHiddenTargets dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GRNWithHiddenTargets dna2 = grnFactory.generateGeneRegulatoryNetwork();
        Hotspot hotspot = new Hotspot(grnSize, hotspotSize);
        return new Individual<>(new SimpleHotspotDiploid(dna1, dna2, mapping, hotspot));
    }

    @Override
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

    @Override
    protected Individual<SimpleHotspotDiploid> generateIndividualWithMatrixHotspot() {
        GRNFactoryWithHiddenTargets grnFactory = new GRNFactoryWithHiddenTargets(targetLength, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(grnSize);
        GRN dna1 = grnFactory.generateGeneRegulatoryNetwork();
        GRN dna2 = grnFactory.generateGeneRegulatoryNetwork();
        MatrixHotspot hotspot = new MatrixHotspot(this.hotspotSize, actualTargetSize * actualTargetSize);
        return new Individual<>(new SimpleHotspotDiploid(dna1, dna2, mapping, hotspot));
    }

    public int getActualTargetSize() {
        return actualTargetSize;
    }

    public int getHiddenTargetSize() {
        return hiddenTargetSize;
    }
}
