package experiments.experiment6;

import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.fitnessFunctions.GRNFitnessFunctionSingleTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a test on why Soto's method generates a flat line.
 * Created by zhenyueqin on 24/6/17.
 */

public class TestField1 {
    public static final int[] target1 = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
    public static final int[] target2 = {1, -1, 1, -1, 1, 1, -1, 1, -1, 1};
//    public static final int[] target3 = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1}; this is the original correct one
    public static final int[] target3 = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};

    public static void main(String[] args) {
        Integer[] tmpArray1 = {-1, -1, 0, -1, 0, 0, 0, 0, 0, 0, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                -1, 0};

        // this is a stable one
        Integer[] tmpArray2 = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
                0, 0, 0};

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


        List<EdgeGene> tmpList1 = getTmpList(tmpArray1);
        List<EdgeGene> tmpList2 = getTmpList(tmpArray2);
        List<EdgeGene> tmpList3 = getTmpList(tmpArray3);
        List<EdgeGene> tmpList4 = getTmpList(tmpArray4);

        Integer[] values = {0, 300};
        List<Integer> tmpThresholds = new ArrayList<>(Arrays.asList(values));

        SimpleMaterial simpleMaterial1 = new SimpleMaterial(tmpList1);
        SimpleMaterial simpleMaterial2 = new SimpleMaterial(tmpList2);
        SimpleMaterial simpleMaterial3 = new SimpleMaterial(tmpList3);
        SimpleMaterial simpleMaterial4 = new SimpleMaterial(tmpList4);

        GRNFitnessFunctionSingleTarget grnFit1 = new GRNFitnessFunctionSingleTarget(
                target1, 100, 300, 0.15);

        int[][] target_1_2 = {target1, target2};
        GRNFitnessFunctionMultipleTargets grnFit2 = new GRNFitnessFunctionMultipleTargets(
                target_1_2, 100, 300, 0.15, tmpThresholds);

        GRNFitnessFunctionSingleTarget grnFit3 = new GRNFitnessFunctionSingleTarget(
                target2, 100, 300, 0.15);

        GRNFitnessFunctionMultipleTargetsFast grnFit4 = new GRNFitnessFunctionMultipleTargetsFast(
                target_1_2, 100, 300, 0.15, 100);

        GRNFitnessFunctionMultipleTargets grnFit5 = new GRNFitnessFunctionMultipleTargets(
                new int[][]{target3, target3}, 100, 300, 0.15, tmpThresholds);

        System.out.println("===Soto's method, we expect the following two are different: ===");
        System.out.println(grnFit2.evaluate(simpleMaterial1, 500));
        System.out.println(grnFit2.evaluate(simpleMaterial1, 500));
//        System.out.println("===Larson's method, we expect the following two are the same: ===");
//        System.out.println(grnFit4.evaluate(simpleMaterial1, 500));
//        System.out.println(grnFit4.evaluate(simpleMaterial1, 500));

        System.out.println("===Max fitness===");
        System.out.println(grnFit1.evaluate(simpleMaterial4));
        System.out.println(grnFit1.evaluate(simpleMaterial4));

        System.out.println("===All Permutations===");
        System.out.println(grnFit1.evaluateAllPotentialAttractors(simpleMaterial4));
    }

    public static List<EdgeGene> getTmpList(Integer[] tmpArray1) {
        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : tmpArray1) {
            tmpList.add(new EdgeGene(e));
        }
        return tmpList;
    }
}
