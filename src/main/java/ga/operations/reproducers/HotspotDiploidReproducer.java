package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.WithHotspot;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public abstract class HotspotDiploidReproducer<C extends Chromosome & WithHotspot> extends DiploidReproducer<C> {
    public HotspotDiploidReproducer() {
        super();
    }

    public HotspotDiploidReproducer(double matchProbability) {
        super(matchProbability);
    }

    public HotspotDiploidReproducer(double matchProbability, boolean toDoCrossover) {
        super(matchProbability, toDoCrossover);
    }

    protected List<Material> crossover(@NotNull SimpleMaterial dna1, SimpleMaterial dna2, C parent) {
        List<Material> newDNAs = new ArrayList<>(2);
        Material dna1Copy = dna1.copy();
        Material dna2Copy = dna2.copy();

        final int crossIndex = getCrossoverIndexByHotspot(parent);
        if (isToDoCrossover) {
            crossoverTwoDNAsAt(dna1Copy, dna2Copy, crossIndex);
        }

        newDNAs.add(dna1Copy);
        newDNAs.add(dna2Copy);
        return newDNAs;
    }

    protected int getCrossoverIndexByHotspot(@NotNull C parent) {
        final double tmpRandom = Math.random();
        SortedSet<Integer> sortedPositions = parent.getHotspot().getSortedHotspotPositions();
        double currentAccumulatedCrossoverRate = 0;
        int crossIndex = -1;

        for (Integer e : sortedPositions) {
            currentAccumulatedCrossoverRate += parent.getHotspot().getRecombinationRateAtPosition(e);
            if (currentAccumulatedCrossoverRate >= tmpRandom) {
                crossIndex = e;
                break;
            }
        }
        return crossIndex;
    }
}
