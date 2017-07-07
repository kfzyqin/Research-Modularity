package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.chromosomes.WithHotspot;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 6/7/17.
 * The Australian National University.
 */
public class SimpleDummyHotspotDiploidReproducer<C extends Chromosome & WithHotspot> extends SimpleHotspotDiploidReproducer {
    public SimpleDummyHotspotDiploidReproducer() {
        super();
    }

    public SimpleDummyHotspotDiploidReproducer(final double matchProbability) {
        super(matchProbability);
    }

    public SimpleDummyHotspotDiploidReproducer(final double matchProbability, final boolean toDoCrossover) {
        super(matchProbability, toDoCrossover);
    }

    @Override
    protected List<SimpleHotspotDiploid> recombine(List<SimpleHotspotDiploid> mates) {
        List<SimpleHotspotDiploid> rtn = new ArrayList<>();

        SimpleHotspotDiploid parent1 = mates.get(0);
        SimpleHotspotDiploid parent2 = mates.get(1);

        List<SimpleMaterial> parent1Gametes = crossoverWithDummyHotspot((C) mates.get(0));
        List<SimpleMaterial> parent2Gametes = crossoverWithDummyHotspot((C) mates.get(1));

        SimpleMaterial dna1_1 = parent1Gametes.get(0).copy();
        SimpleMaterial dna1_2 = parent1Gametes.get(1).copy();
        SimpleMaterial dna2_1 = parent2Gametes.get(0).copy();
        SimpleMaterial dna2_2 = parent2Gametes.get(1).copy();

        ExpressionMap mapping1 = parent1.getMapping().copy();
        ExpressionMap mapping2 = parent2.getMapping().copy();

        Hotspot hotspot1 = parent1.getHotspot().copy();

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
        rtn.add(new SimpleHotspotDiploid(dna1_1, dna1_2, mapping1, hotspot1));
//        rtn.add(new SimpleDiploid(dna2_1, dna2_2, mapping2.copy()));
        return rtn;
    }

    protected List<SimpleMaterial> crossoverWithDummyHotspot(@NotNull final C parent) {
        List<SimpleMaterial> newDNAs = new ArrayList<>(2);
        List<SimpleMaterial> materialView = parent.getMaterialsView();
        SimpleMaterial dna1Copy = materialView.get(0).copy();
        SimpleMaterial dna2Copy = materialView.get(1).copy();

        List<Integer> tmpHotspotPositions = new ArrayList<Integer>(parent.getHotspot().getSortedHotspotPositions());
        final int crossIndex = tmpHotspotPositions.get(
                ThreadLocalRandom.current().nextInt(0, tmpHotspotPositions.size()));

        if (isToDoCrossover) {
            crossoverTwoDNAsAt(dna1Copy, dna2Copy, crossIndex);
        }

        newDNAs.add(dna1Copy);
        newDNAs.add(dna2Copy);
        return newDNAs;
    }
}
