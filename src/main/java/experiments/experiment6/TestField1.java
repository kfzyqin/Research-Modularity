package experiments.experiment6;

import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsHidden;
import ga.operations.fitnessFunctions.GRNFitnessFunctionSingleTarget;
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
            -1, 1, -1, 1, -1,
            1, -1, 1, -1, 1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target3 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };

    private static final int[] target4 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target5 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    public static void main(String[] args) {
        Integer[] tmpArray1 = {1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
                1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1, -1,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0,
                -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1};

        // this is a stable one
        Integer[] tmpArray2 = {0, -1, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0,
                0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 1, -1,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1,
                0, -1, 1, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 1, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0,
                0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 1, 0, 1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
                -1, 0, -1, 0, 1, -1, 0, 0, 0, 0, 0, -1, 0, 0, -1, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1,
                0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 1, -1, 0, 0, 0};

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

        Integer[] conservation1 = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 1, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, -1, 1,
                0, 0, 0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, -1, 0, 0, 0,
                0, 0, 1, -1, 1, -1, 0
        };

        Integer[] conservation2 = {0, 0, 0, 0, 1, 0, -1, 0, 0, 0, -1, 0, -1, 0, -1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0,
                0, 0, 0, -1, 1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 1, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1,
                0, -1, 0, 0, 0, 0, -1, 1, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0,
                0, 0, 0, 1, -1, 1, -1, 1
        };

        Integer[] conservation3 = {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                0, 0, -1, 0, -1, 0, -1, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 1, 0, 0, -1, -1, 0, 0, 0,
                0, 1, 0, 0, -1, 1
        };

        Integer[] conservation4 = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, -1, 1, 0, 0, 0, 1, 0, 0,
                0, -1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0,
                -1, 0, 0, 0, 0, -1, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 1, 0, 0, 0, 0, 0, -1, 0, -1, 1, 0, 0, 0,
                0, 0, 0, 1, -1, 0, -1, 0
        };

        Integer[] conservation5 = {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, -1, 1, -1, 1, -1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 1,
                0, 0, 0, 0, 0, -1, 0, -1, -1, 0, 0, 0, 1, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0,
                0, 0, 1, 0, 1, -1, 0
        };

        Integer[] conservation6 = {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, -1, -1, 0, -1, 1, -1, 0, 0, -1, 0, 0, 0, -1, 1, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1,
                0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 1, 0, 0,
                0, 1, -1, 0, -1, 0
        };

        Integer[] conservation7 = {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, -1, 1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0,
                0, 0, 0, -1, 1, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, -1, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0,
                1, -1, 1, 0, 1
        };

        Integer[] conservation8 = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0, -1, 0, 0, 0, 0, -1, 0, -1, 0, 0, 1, 0, 0,
                0, 0, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 1, -1, 1, -1, 0, 0, -1, 0, 0, 1, -1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0,
                0, 1, 0, 1, 0, 0
        };

        Integer[] conservation9 = {0, 0, 0, -1, 0, 0, 0, 0, 1, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0,
                0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, -1, 0, 0, 0,
                0, 0, 1, 0, 1, -1, 1
        };

        Integer[] conservation10 = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, -1, 0, -1, 0, -1, -1, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 0, 0, 1, -1, 0, -1, 0, 0, 0, 0, 0, 1, 0, 1, -1, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                0, -1, 1, 0, 1
        };


        Integer[] conservationHidden1 = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 1,
                0, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 0, 1, 0, 0, 0, 0, -1, 1, -1, 0, 0, 0, 0, -1, 0, -1, 0,
                0, 0, 0, 0, 1, 0, 1, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 1, -1, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0,
                -1, 0, -1, 1, 0, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, -1, 0, 0, -1, 1, 0, 0,
                0, 0, 0, 0, 0, -1, 0, 1, 0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        List<EdgeGene> tmpList1 = getTmpList(tmpArray1);
        List<EdgeGene> tmpList2 = getTmpList(tmpArray2);
        List<EdgeGene> tmpList3 = getTmpList(tmpArray3);
        List<EdgeGene> tmpList4 = getTmpList(tmpArray4);

        List<EdgeGene> tmpList5 = getTmpList(conservation1);
        List<EdgeGene> tmpList6 = getTmpList(conservation2);
        List<EdgeGene> tmpList7 = getTmpList(conservation3);
        List<EdgeGene> tmpList8 = getTmpList(conservation4);
        List<EdgeGene> tmpList9 = getTmpList(conservation5);
        List<EdgeGene> tmpList10 = getTmpList(conservation6);
        List<EdgeGene> tmpList11 = getTmpList(conservation7);
        List<EdgeGene> tmpList12 = getTmpList(conservation8);
        List<EdgeGene> tmpList13 = getTmpList(conservation9);
        List<EdgeGene> tmpList14 = getTmpList(conservation10);

        List<EdgeGene> tmpHiddenList1 = getTmpList(conservationHidden1);

        Integer[] values = {0, 500, 2000};
        List<Integer> tmpThresholds = new ArrayList<>(Arrays.asList(values));

        Integer[] values2 = {0, 500};
        List<Integer> tmpThresholds2 = new ArrayList<>(Arrays.asList(values2));

        SimpleMaterial simpleMaterial1 = new SimpleMaterial(tmpList1);
        SimpleMaterial simpleMaterial2 = new SimpleMaterial(tmpList2);
        SimpleMaterial simpleMaterial3 = new SimpleMaterial(tmpList3);
        SimpleMaterial simpleMaterial4 = new SimpleMaterial(tmpList4);

        SimpleMaterial simpleMaterial5 = new SimpleMaterial(tmpList5);
        SimpleMaterial simpleMaterial6 = new SimpleMaterial(tmpList6);
        SimpleMaterial simpleMaterial7 = new SimpleMaterial(tmpList7);
        SimpleMaterial simpleMaterial8 = new SimpleMaterial(tmpList8);
        SimpleMaterial simpleMaterial9 = new SimpleMaterial(tmpList9);
        SimpleMaterial simpleMaterial10 = new SimpleMaterial(tmpList10);
        SimpleMaterial simpleMaterial11 = new SimpleMaterial(tmpList11);
        SimpleMaterial simpleMaterial12 = new SimpleMaterial(tmpList12);
        SimpleMaterial simpleMaterial13 = new SimpleMaterial(tmpList13);
        SimpleMaterial simpleMaterial14 = new SimpleMaterial(tmpList14);

        SimpleMaterial simpleHiddenMaterial1 = new SimpleMaterial(tmpHiddenList1);

        SimpleMaterial[] conservationMaterials = {
                simpleMaterial5, simpleMaterial6, simpleMaterial7,
                simpleMaterial8, simpleMaterial9, simpleMaterial10,
                simpleMaterial11, simpleMaterial12, simpleMaterial13,
                simpleMaterial14
        };

        GRNFitnessFunctionSingleTarget grnFit1 = new GRNFitnessFunctionSingleTarget(
                target1, 100, 300, 0.15);

        int[][] target_1_2_3 = {target1, target2, target3};
        GRNFitnessFunctionMultipleTargets grnFit2 = new GRNFitnessFunctionMultipleTargets(
                target_1_2_3, 100, 300, 0.15, tmpThresholds);

        int[][] target_4_5 = {target4, target5};
        GRNFitnessFunctionMultipleTargets grnFit7 = new GRNFitnessFunctionMultipleTargets(
                target_4_5, 100, 500, 0.15, tmpThresholds2);

        GRNFitnessFunctionSingleTarget grnFit3 = new GRNFitnessFunctionSingleTarget(
                target2, 100, 300, 0.15);

        GRNFitnessFunctionMultipleTargetsFast grnFit4 = new GRNFitnessFunctionMultipleTargetsFast(
                target_1_2_3, 100, 300, 0.15, 100);

        GRNFitnessFunctionMultipleTargets grnFit5 = new GRNFitnessFunctionMultipleTargets(
                new int[][]{target3, target3}, 100, 300, 0.15, tmpThresholds);

        GRNFitnessFunctionMultipleTargets grnFit6 = new GRNFitnessFunctionMultipleTargets(
                new int[][]{target3, target3}, 20, 75, 0.15, tmpThresholds);

        GRNFitnessFunctionMultipleTargetsHidden grnHidden1 = new GRNFitnessFunctionMultipleTargetsHidden(
                target_4_5, 100, 500, 0.15, tmpThresholds2, 5
        );

        System.out.println("===Soto's method, we expect the following two are different: ===");
        System.out.println(grnFit2.evaluate(simpleMaterial1, 2904));
        System.out.println(grnFit2.evaluate(simpleMaterial2, 2782));


        System.out.println("===Conservation: ===");
        for (SimpleMaterial e : conservationMaterials) {
            System.out.println(grnFit7.evaluate(e, 1999));
        }

        Reproducer<SimpleHaploid> reproducer = new GRNHaploidMatrixDiagonalReproducer(target1.length);

//        for (int i=0; i<10; i++) {
//            System.out.println("crossover at: " + i);
//            SimpleMaterial[] twoNewMaterials = testCrossover(i, simpleMaterial5, simpleMaterial6);
//            System.out.println("Children 1: " + grnFit7.evaluate((SimpleMaterial) twoNewMaterials[0], 1999));
//            System.out.println("Children 2: " + grnFit7.evaluate((SimpleMaterial) twoNewMaterials[1], 1999));
//        }

        List<Double> childrenFitness = new ArrayList<>();
        List<Double> averageFitness = new ArrayList<>();

        for (int i=0; i<conservationMaterials.length; i++) {
            for (int j=0; j<i; j++) {
                Double parentFitness1 = grnFit7.evaluate(conservationMaterials[i], 1999);
                Double parentFitness2 = grnFit7.evaluate(conservationMaterials[j], 1999);

                SimpleMaterial[] twoNewMaterials = testCrossover(5, conservationMaterials[i], conservationMaterials[j], conservationMaterials.length);
                Double fitness1 = grnFit7.evaluate((SimpleMaterial) twoNewMaterials[0], 1999);
                Double fitness2 = grnFit7.evaluate((SimpleMaterial) twoNewMaterials[1], 1999);
//                System.out.println("Children 1: " + fitness1);
//                System.out.println("Children 2: " + fitness2);
                childrenFitness.add(fitness1 > fitness2 ? fitness1 : fitness2);
                averageFitness.add(parentFitness1 > parentFitness2 ? parentFitness1 : parentFitness2);
            }
        }

        System.out.println(averageFitness);
        System.out.println(childrenFitness);

        System.out.println("===hidden starts from here: ===");
        System.out.println(grnHidden1.evaluate(simpleHiddenMaterial1, 1999));


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
