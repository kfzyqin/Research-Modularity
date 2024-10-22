package ga.operations.reproducers;

import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.WithHotspot;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import org.jetbrains.annotations.NotNull;

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

    @Override
    protected List<SimpleMaterial> crossover(@NotNull final C parent) {
        List<SimpleMaterial> newDNAs = new ArrayList<>(2);
        List<SimpleMaterial> materialView = parent.getMaterialsView();
        SimpleMaterial dna1Copy = materialView.get(0).copy();
        SimpleMaterial dna2Copy = materialView.get(1).copy();

        final int crossIndex = getCrossoverIndexByHotspot(parent);

        if (isToDoCrossover) {
            crossoverTwoDNAsAt(dna1Copy, dna2Copy, crossIndex);
        }

        newDNAs.add(dna1Copy);
        newDNAs.add(dna2Copy);
        return newDNAs;
    }

    /*
    this method will potentially swap every allele.
     */
    protected List<SimpleMaterial> throughCrossover(@NotNull final C parent) {
        List<SimpleMaterial> newDNAs = new ArrayList<>(2);
        List<SimpleMaterial> materialView = parent.getMaterialsView();
        SimpleMaterial dna1Copy = materialView.get(0).copy();
        SimpleMaterial dna2Copy = materialView.get(1).copy();

        if (isToDoCrossover) {
            for (int crossIndex : parent.getHotspot().getSortedHotspotPositions()) {
                if (Math.random() < parent.getHotspot().getRecombinationRateAtPosition(crossIndex)) {
                    crossoverTwoDNAsAt(dna1Copy, dna2Copy, crossIndex);
                }
            }
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

    protected int deterministicGetCrossoverIndexByHotspot(@NotNull C parent) {
        double maxRate = 0;
        int maxRateIndex = -1;
        SortedSet<Integer> sortedPositions = parent.getHotspot().getSortedHotspotPositions();
        for (Integer e : sortedPositions) {
            if (parent.getHotspot().getRecombinationRateAtPosition(e) > maxRate) {
                maxRate = parent.getHotspot().getRecombinationRateAtPosition(e);
                maxRateIndex = e;
            }
        }
        return maxRateIndex;
    }
}
