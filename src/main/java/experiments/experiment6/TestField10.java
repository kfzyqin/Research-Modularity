package experiments.experiment6;

import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 6/7/17.
 * The Australian National University.
 */
public class TestField10 {
    private static final int[] target1 = {
            1, -1, 1
    };
    private static final int[] target2 = {
            1, -1, 1
    };
    private static final int[] target3 = {
            1, -1, 1
    };

    public static void main(String[] args) {
        int[][] targets = {target1, target2, target3};

        Integer[] tmpArray1 = {1, 1, 1, 0, 0, 0, -1, -1, -1};
        List<EdgeGene> tmpList1 = TestField1.getTmpList(tmpArray1);
        SimpleMaterial simpleMaterial1 = new SimpleMaterial(tmpList1);
        Integer[] values = {0, 300};
        List<Integer> tmpThresholds = new ArrayList<>(Arrays.asList(values));

        GRNFitnessFunctionMultipleTargetsFast aFitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(
                targets, 3, 10, 0.15, tmpThresholds, 100
        );

        aFitnessFunction.evaluate(simpleMaterial1, 0);
    }
}
