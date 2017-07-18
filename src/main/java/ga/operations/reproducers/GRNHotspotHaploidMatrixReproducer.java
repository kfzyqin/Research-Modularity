package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.chromosomes.SimpleHotspotHaploid;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/7/17.
 * The Australian National University.
 */
public class GRNHotspotHaploidMatrixReproducer extends HaploidReproducer<SimpleHotspotHaploid> {
    private final int matrixSideSize;

    public GRNHotspotHaploidMatrixReproducer(int matrixSideSize) {
        this.matrixSideSize = matrixSideSize;
    }

    @Override
    public List<SimpleHotspotHaploid> reproduce(@NotNull List<SimpleHotspotHaploid> mates) {
        List<SimpleHotspotHaploid> children = new ArrayList<>(2);

        SimpleHotspotHaploid child1 = mates.get(0).copy();
        SimpleHotspotHaploid child2 = mates.get(1).copy();

        SimpleMaterial dna1 = child1.getMaterialsView().get(0);
        SimpleMaterial dna2 = child2.getMaterialsView().get(0);

        final int crossIndex = getCrossoverIndexByHotspot(Math.random() < 0.5 ? child1 : child2);
//        final int crossIndex = 5;
        for (int currentCrossIndex=0; currentCrossIndex<crossIndex; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < crossIndex * matrixSideSize) {
                crossoverTwoDNAsAtPosition(dna1, dna2, tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        for (int currentCrossIndex=crossIndex; currentCrossIndex<matrixSideSize; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex + crossIndex * matrixSideSize;
            while (tmpCrossIndex < matrixSideSize * matrixSideSize) {
                crossoverTwoDNAsAtPosition(dna1, dna2, tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        children.add(child1);
        children.add(child2);

        return children;
    }

    protected int getCrossoverIndexByHotspot(@NotNull SimpleHotspotHaploid parent) {
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
