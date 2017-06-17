package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.components.genes.Gene;
import ga.components.materials.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public abstract class GenderHotspotReproducer <G extends Chromosome & CoupleableWithHotspot> extends GenderReproducer<G> {

    public GenderHotspotReproducer(int numOfChildren) {
        super(numOfChildren);
    }

    protected List<Material> crossover(@NotNull final G parent) {
        List<Material> gametes = new ArrayList<>(2);
        List<Material> materialView = parent.getMaterialsView();
        Material dna1 = materialView.get(0).copy();
        Material dna2 = materialView.get(1).copy();

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

        this.crossoverTwoDNAsAt(dna1, dna2, crossIndex);

        gametes.add(dna1);
        gametes.add(dna2);
        return gametes;
    }
}
