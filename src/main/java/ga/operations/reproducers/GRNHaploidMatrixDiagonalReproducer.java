package ga.operations.reproducers;

import ga.components.chromosomes.SimpleHaploid;
import ga.components.materials.SimpleMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/7/17.
 * The Australian National University.
 */
public class GRNHaploidMatrixDiagonalReproducer extends HaploidReproducer<SimpleHaploid> {
    private final int matrixSideSize;
    private long seed = 100;

    public GRNHaploidMatrixDiagonalReproducer(int matrixSideSize) {
        this.matrixSideSize = matrixSideSize;
    }

    @Override
    public List<SimpleHaploid> reproduce(@NotNull List<SimpleHaploid> mates) {
        List<SimpleHaploid> children = new ArrayList<>(2);

        SimpleHaploid child1 = mates.get(0).copy();
        SimpleHaploid child2 = mates.get(1).copy();

        SimpleMaterial dna1 = child1.getMaterialsView().get(0);
        SimpleMaterial dna2 = child2.getMaterialsView().get(0);

        // for seed
//        Random r = new Random();
//        seed += 1000;
//        r.setSeed(seed);
//        final int crossIndex = r.nextInt(matrixSideSize);

        final int crossIndex = ThreadLocalRandom.current().nextInt(matrixSideSize);
//        final int crossIndex = 3;
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


}
