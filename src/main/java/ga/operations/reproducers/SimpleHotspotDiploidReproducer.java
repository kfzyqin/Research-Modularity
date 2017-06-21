package ga.operations.reproducers;

import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class SimpleHotspotDiploidReproducer extends HotspotDiploidReproducer<SimpleHotspotDiploid> {

    public SimpleHotspotDiploidReproducer() {
        super();
    }

    public SimpleHotspotDiploidReproducer(double matchProbability) {
        super(matchProbability);
    }

    public SimpleHotspotDiploidReproducer(double matchProbability, boolean toDoCrossover) {
        super(matchProbability, toDoCrossover);
    }

    @Override
    protected List<SimpleHotspotDiploid> recombine(List<SimpleHotspotDiploid> mates) {
        List<SimpleHotspotDiploid> rtn = new ArrayList<>();
        SimpleHotspotDiploid parent1 = mates.get(0);
        SimpleHotspotDiploid parent2 = mates.get(1);
        SimpleMaterial dna1_1 = parent1.getMaterialsView().get(0).copy();
        SimpleMaterial dna1_2 = parent2.getMaterialsView().get(0).copy();
        SimpleMaterial dna2_1 = parent1.getMaterialsView().get(1).copy();
        SimpleMaterial dna2_2 = parent2.getMaterialsView().get(1).copy();
        ExpressionMap mapping1 = parent1.getMapping();
        ExpressionMap mapping2 = parent2.getMapping();
        Hotspot hotspot1 = parent1.getHotspot();
        Hotspot hotspot2 = parent2.getHotspot();

        if (Math.random() > matchProbability) {
            SimpleMaterial tmp = dna1_2;
            dna1_2 = dna2_2;
            dna2_2 = tmp;
        }

        if (Math.random() > matchProbability) {
            ExpressionMap tmp = mapping1;
            mapping1 = mapping2;
            mapping2 = tmp;
        }

        if (Math.random() > matchProbability) {
            Hotspot tmp = hotspot1;
            hotspot1 = hotspot2;
            hotspot2 = tmp;
        }

        rtn.add(new SimpleHotspotDiploid(dna1_1, dna1_2, mapping1, hotspot1));
        rtn.add(new SimpleHotspotDiploid(dna2_1, dna2_2, mapping2, hotspot2));
        return rtn;
    }
}
