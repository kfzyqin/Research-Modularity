package ga.operations.reproducers;

import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 21/6/17.
 * The Australian National University.
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
    /*
    Todo: Rewrite this with David's approach
     */
    protected List<SimpleHotspotDiploid> recombine(List<SimpleHotspotDiploid> mates) {
        // Todo: Ask Bob about whether here should be two children.
        List<SimpleHotspotDiploid> rtn = new ArrayList<>();

        SimpleHotspotDiploid parent1 = mates.get(0);
        SimpleHotspotDiploid parent2 = mates.get(1);

        List<SimpleMaterial> parent1Gametes = crossover(mates.get(0));
        List<SimpleMaterial> parent2Gametes = crossover(mates.get(1));

//        List<SimpleMaterial> parent1Gametes = throughCrossover(mates.get(0));
//        List<SimpleMaterial> parent2Gametes = throughCrossover(mates.get(1));

        SimpleMaterial dna1_1 = parent1Gametes.get(0).copy();
        SimpleMaterial dna1_2 = parent1Gametes.get(0).copy();
        SimpleMaterial dna2_1 = parent2Gametes.get(1).copy();
        SimpleMaterial dna2_2 = parent2Gametes.get(1).copy();

        ExpressionMap mapping1 = parent1.getMapping().copy();
        ExpressionMap mapping2 = parent2.getMapping().copy();

        Hotspot hotspot1 = parent1.getHotspot().copy();
        Hotspot hotspot2 = parent2.getHotspot().copy();

        if (Math.random() < matchProbability) {
            SimpleMaterial tmp = dna1_2;
            dna1_2 = dna2_2;
            dna2_2 = tmp;
        }

        if (Math.random() < matchProbability) {
            ExpressionMap tmp = mapping1;
            mapping1 = mapping2;
            mapping2 = tmp;
        }

        if (Math.random() < matchProbability) {
            Hotspot tmp = hotspot1;
            hotspot1 = hotspot2;
            hotspot2 = tmp;
        }

        rtn.add(new SimpleHotspotDiploid(dna1_1, dna1_2, mapping1, hotspot1));
//        rtn.add(new SimpleHotspotDiploid(dna2_1, dna2_2, mapping2, hotspot2));
        return rtn;
    }
}
