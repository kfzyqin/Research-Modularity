package ga.operations.reproducers;

import ga.components.chromosomes.SimpleHaploid;
import ga.components.materials.SimpleMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/7/17.
 * The Australian National University.
 */
public class GRNHaploidMatrixHorizontalReproducer extends HaploidReproducer<SimpleHaploid> {
    private final int matrixSideSize;

    public GRNHaploidMatrixHorizontalReproducer(int matrixSideSize) {
        this.matrixSideSize = matrixSideSize;
    }

    @Override
    public List<SimpleHaploid> reproduce(@NotNull List<SimpleHaploid> mates) {
        List<SimpleHaploid> children = new ArrayList<>(2);

        SimpleHaploid child1 = mates.get(0).copy();
        SimpleHaploid child2 = mates.get(1).copy();

        SimpleMaterial dna1 = child1.getMaterialsView().get(0);
        SimpleMaterial dna2 = child2.getMaterialsView().get(0);

        final int crossIndex = ThreadLocalRandom.current().nextInt(matrixSideSize);
//        final int crossIndex = 3;
        for (int currentCrossIndex=crossIndex*matrixSideSize; currentCrossIndex<matrixSideSize*matrixSideSize; currentCrossIndex+=matrixSideSize) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < currentCrossIndex + matrixSideSize) {
                crossoverTwoDNAsAtPosition(dna1, dna2, tmpCrossIndex);
                tmpCrossIndex += 1;
            }
        }

        children.add(child1);
        children.add(child2);

        return children;
    }


}
