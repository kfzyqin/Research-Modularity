package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GRNFactoryWithHiddenTargets;
import ga.components.materials.GRNWithHiddenTargets;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;
import ga.others.GeneralMethods;

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
}
