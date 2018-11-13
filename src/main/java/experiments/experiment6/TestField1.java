package experiments.experiment6;

import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.*;
import ga.operations.reproducers.GRNHaploidMatrixDiagonalReproducer;
import ga.operations.reproducers.Reproducer;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a test on why Soto's method generates a flat line.
 * Created by zhenyueqin on 24/6/17.
 */

public class TestField1 {


    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 30;

    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7};
    private static final double stride = 0.00;
    private static final int perturbations = 500;

    public static void main(String[] args) {
        int[][] targets = {target1, target2};

        Integer[] tmpArray1 = {1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
                1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1, -1,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0,
                -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1};

        // this is a stable one
        Integer[] tmpArray1_1 = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, -1, 0, 1, 0, 0, -1,
                0, 0, 0, 0, 1, 0, -1, 0, -1, 0, 0, 1, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0,
                0, 0};

        Integer[] tmpArray1_2 = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0,
                0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 0, 0, 0, 0, 0, 0,
                -1, 1, -1, 0};

        // this is not stable, fitness: 0.8299172167220299
        Integer[] tmpArray3 = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, -1, 0, 1, 0, 1, 0, 1, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0,
                0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                -1, 0
        };

        // very bad, fitness: 0.5701851309090582
        Integer[] tmpArray4 = {0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, -1, 1, 1, 0, 1, 0, 0, 0, 1,
                0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
                0, -1, 0, 1, -1, 0
        };

        List<EdgeGene> tmpList2 = getTmpList(tmpArray1_2);

        SimpleMaterial simpleMaterial1 = new SimpleMaterial(tmpList2);

        FitnessFunction grnFit1 = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);


//        System.out.println("===Soto's method, we expect the following two are different: ===");
        System.out.println(grnFit1.evaluate(simpleMaterial1));


    }

    public static List<EdgeGene> getTmpList(Integer[] tmpArray1) {
        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : tmpArray1) {
            tmpList.add(new EdgeGene(e));
        }
        return tmpList;
    }

    public static SimpleMaterial[] testCrossover(int crossIndex, SimpleMaterial dna1, SimpleMaterial dna2,
                                                 int matrixSideSize) {
        SimpleMaterial dna1Copy = dna1.copy();
        SimpleMaterial dna2Copy = dna2.copy();

        for (int currentCrossIndex=0; currentCrossIndex<crossIndex; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < crossIndex * matrixSideSize) {
                crossoverTwoDNAsAtPosition(dna1Copy, dna2Copy, tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        for (int currentCrossIndex=crossIndex; currentCrossIndex<matrixSideSize; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex + crossIndex * matrixSideSize;
            while (tmpCrossIndex < matrixSideSize * matrixSideSize) {
                crossoverTwoDNAsAtPosition(dna1Copy, dna2Copy, tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        return new SimpleMaterial[]{dna1Copy, dna2Copy};
    }

    public static void crossoverTwoDNAsAtPosition(Material dna1, Material dna2, int index) {
        final int i1 = ((Gene<Integer>) dna1.getGene(index)).getValue();
        final int i2 = ((Gene<Integer>) dna2.getGene(index)).getValue();
        dna1.getGene(index).setValue(i2);
        dna2.getGene(index).setValue(i1);
    }

}
