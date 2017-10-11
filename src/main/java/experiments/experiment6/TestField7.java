package experiments.experiment6;

import ga.components.materials.GRN;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFastHidden;
import tests.IntegrationTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhenyueqin on 27/6/17.
 */
public class TestField7 {
    public static final int[] target1 = {1, -1};
    public static final int[] target2 = {1, 1};

    public static void main(String[] args) {
        Integer[] values = {0, 300};
        List<Integer> tmpThresholds = new ArrayList<>(Arrays.asList(values));

        GRNFitnessFunctionMultipleTargetsFastHidden grnFit2 = new GRNFitnessFunctionMultipleTargetsFastHidden(
                new int[][]{target1, target2}, 100, 300, 0.15,
                tmpThresholds, 100, 1);

        List<Integer> aGRNList = Arrays.asList(1, -1, -1, 1, -1, 1, 0, 1, -1);
//        List<Integer> aGRNList = Arrays.asList(0, 0, -1, 0, 1, 1, 0, -1, -1);

        GRN grn = new GRN(IntegrationTest.convertIntegerListToEdgeGeneList(aGRNList));

        double aFitness = grnFit2.evaluate(grn, 400);

        System.out.println(aFitness);
    }
}
