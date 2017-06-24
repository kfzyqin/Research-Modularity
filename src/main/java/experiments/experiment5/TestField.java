package experiments.experiment5;

import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionWithMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionWithMultipleTargetsFaster;
import ga.operations.fitnessFunctions.GRNFitnessFunctionWithSingleTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class TestField {
    public static final int[] target1 = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
    public static final int[] target2 = {1, -1, 1, -1, 1, 1, -1, 1, -1, 1};


    public static void main(String[] args) {
//        Integer[] tmpArray = {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};

//        Integer[] tmpArray = {0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
//                -1, 0};

        Integer[] tmpArray = {-1, -1, 0, -1, 0, 0, 0, 0, 0, 0, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                -1, 0};


        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : tmpArray) {
            tmpList.add(new EdgeGene(e));
        }

        Integer[] values = {0, 300};
        List<Integer> tmpThresholds = new ArrayList<>(Arrays.asList(values));

        SimpleMaterial simpleMaterial = new SimpleMaterial(tmpList);

        GRNFitnessFunctionWithSingleTarget grnFit1 = new GRNFitnessFunctionWithSingleTarget(
                target1, 100, 300, 0.15);

        GRNFitnessFunctionWithMultipleTargets grnFit2 = new GRNFitnessFunctionWithMultipleTargets(
                target1, target2, 100, 300, 0.15, tmpThresholds);

        GRNFitnessFunctionWithSingleTarget grnFit3 = new GRNFitnessFunctionWithSingleTarget(
                target2, 100, 300, 0.15);

        GRNFitnessFunctionWithMultipleTargetsFaster grnFit4 = new GRNFitnessFunctionWithMultipleTargetsFaster(
                target1, target2, 100, 300, 0.15, 100);

        System.out.println(grnFit1.evaluate(simpleMaterial));
        System.out.println(grnFit3.evaluate(simpleMaterial));
        System.out.println(grnFit2.evaluate(simpleMaterial, 500));
        System.out.println(grnFit4.evaluate(simpleMaterial, 500));

    }
}
