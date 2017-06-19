package experiments.experiment4;

import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionWithMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionWithSingleTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class TestField {
    public static final int[] target1 = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};


    public static void main(String[] args) {
        Integer[] tmpArray = {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : tmpArray) {
            tmpList.add(new EdgeGene(e));
        }

        SimpleMaterial simpleMaterial = new SimpleMaterial(tmpList);

        GRNFitnessFunctionWithSingleTarget grnFit1 = new GRNFitnessFunctionWithSingleTarget(
                target1, 100, 300, 0.15);
        GRNFitnessFunctionWithMultipleTargets grnFit2 = new GRNFitnessFunctionWithMultipleTargets(
                target1, target1, 100, 300, 0.15);
        System.out.println(grnFit1.evaluate(simpleMaterial));
        System.out.println(grnFit2.evaluate(simpleMaterial, 0));

        System.out.println(System.getProperty("user.dir"));
    }
}
