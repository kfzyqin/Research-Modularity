package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.WithHotspot;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 4/7/17.
 * The Australian National University.
 */
public abstract class HotspotDiploidMatrixReproducer<C extends Chromosome & WithHotspot> extends HotspotDiploidReproducer<C> {
    protected final int matrixSideSize;

    public HotspotDiploidMatrixReproducer(final int matrixSideSize) {
        super();
        this.matrixSideSize = matrixSideSize;
    }

    public HotspotDiploidMatrixReproducer(final double matchProbability, final int matrixSideSize) {
        super(matchProbability);
        this.matrixSideSize = matrixSideSize;
    }

    public HotspotDiploidMatrixReproducer(final double matchProbability, final boolean toDoCrossover, final int matrixSideSize) {
        super(matchProbability, toDoCrossover);
        this.matrixSideSize = matrixSideSize;
    }

    protected List<SimpleMaterial> crossoverMatrix(@NotNull final C parent) {
        List<SimpleMaterial> newDNAs = new ArrayList<>(2);
        List<SimpleMaterial> materialView = parent.getMaterialsView();
        SimpleMaterial dna1Copy = materialView.get(0).copy();
        SimpleMaterial dna2Copy = materialView.get(1).copy();

        if (isToDoCrossover) {
            for (int crossIndex=getCrossoverIndexByHotspot(parent); crossIndex<matrixSideSize; crossIndex++) {
//                if (Math.random() < parent.getHotspot().getRecombinationRateAtPosition(crossIndex)) {
                    for (int currentCrossIndex=crossIndex; currentCrossIndex<matrixSideSize; currentCrossIndex++) {
                        int tmpCrossIndex = currentCrossIndex;
                        while (tmpCrossIndex < matrixSideSize * matrixSideSize) {
                            crossoverTwoDNAsAtPosition(dna1Copy, dna2Copy, tmpCrossIndex);
                            tmpCrossIndex += matrixSideSize;
                        }
                    }
//                }
                break;
            }

        }

        newDNAs.add(dna1Copy);
        newDNAs.add(dna2Copy);
        return newDNAs;
    }
}
