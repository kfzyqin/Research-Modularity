package ga.operations.reproducers;

import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 4/7/17.
 * The Australian National University.
 */
public class GRNDiploidFixedMatrixReproducer extends GRNDiploidMatrixReproducer {

    public GRNDiploidFixedMatrixReproducer(int matrixSideSize) {
        super(matrixSideSize);
    }

    public GRNDiploidFixedMatrixReproducer(double matchProbability, int matrixSideSize) {
        super(matchProbability, matrixSideSize);
    }

    public GRNDiploidFixedMatrixReproducer(double matchProbability, boolean toDoCrossover, int matrixSideSize) {
        super(matchProbability, toDoCrossover, matrixSideSize);
    }

    protected List<SimpleMaterial> crossoverMatrix(@NotNull final SimpleDiploid parent) {
        List<SimpleMaterial> newDNAs = new ArrayList<>(2);
        List<SimpleMaterial> materialView = parent.getMaterialsView();
        SimpleMaterial dna1Copy = materialView.get(0).copy();
        SimpleMaterial dna2Copy = materialView.get(1).copy();

        if (isToDoCrossover) {
            final int crossIndex = 5;
            for (int currentCrossIndex = 0; currentCrossIndex < crossIndex; currentCrossIndex++) {
                int tmpCrossIndex = currentCrossIndex;
                while (tmpCrossIndex < crossIndex * matrixSideSize) {
                    crossoverTwoDNAsAtPosition(dna1Copy, dna2Copy, tmpCrossIndex);
                    tmpCrossIndex += matrixSideSize;
                }
            }

            for (int currentCrossIndex = crossIndex; currentCrossIndex < matrixSideSize; currentCrossIndex++) {
                int tmpCrossIndex = currentCrossIndex + crossIndex * matrixSideSize;
                while (tmpCrossIndex < matrixSideSize * matrixSideSize) {
                    crossoverTwoDNAsAtPosition(dna1Copy, dna2Copy, tmpCrossIndex);
                    tmpCrossIndex += matrixSideSize;
                }
            }
        }
        newDNAs.add(dna1Copy);
        newDNAs.add(dna2Copy);
        return newDNAs;
    }
}
