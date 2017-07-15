package tests;

import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFastHidden;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


/**
 * Created by Zhenyue Qin (秦震岳) on 14/7/17.
 * The Australian National University.
 */
public class GRNFitnessFunctionTest {
    public static final int[] target1 = {1, 1, -1};
    public static final int[] target2 = {1, -1, 1};
    public static final int[] target3 = {-1, 1, 1};

    public static final int[][] targets = {target1, target2, target3};

    GRNFitnessFunctionMultipleTargetsFast fitnessFunctionFast = new GRNFitnessFunctionMultipleTargetsFast(
            targets, 1, 4, 0.15, 2
    );

    GRNFitnessFunctionMultipleTargetsFastHidden fitnessFunctionFastHidden = new
            GRNFitnessFunctionMultipleTargetsFastHidden(
                    targets, 1, 4, 0.15, 2, 3);

    @Test
    public void testGetHammingDistanceWithHiddenTargets() {
        DataGene[] aState = generateOneDeterministicPerturbationTargets(0, 5);

        int[][] tmpTargets = {
                {-1, -1, -1, 1, 1},
                {-1, -1, 1, 1, 1},
                {1, 1, 1, 1, 1},
        };

        int count = 0;
        for (int[] tmpTarget : tmpTargets) {
            double distance = fitnessFunctionFastHidden.getHammingDistanceWithHiddenTargets(
                    aState, tmpTarget, 2);
            switch (count) {
                case 0:
                    assertTrue(distance == 0);
                    break;
                case 1:
                    assertTrue(distance == 1);
                    break;
                case 2:
                    assertTrue(distance == 3);
                    break;
            }
            count += 1;
        }
    }

    @Test
    public void testEvaluateOneTarget() {
        SimpleMaterial grn = generateGRN();
        DataGene[][] perturbationTargets =  generateDeterministicPerturbationTargets(4);
        double fitness1 = fitnessFunctionFast.evaluateOneTarget(grn, target1, perturbationTargets);
        assertTrue(fitness1 == 0);
    }

    @Test
    public void testUpdateState() {
        SimpleMaterial grn = generateGRN();
        for (int i : new int[]{0, 1}) {
            DataGene[] initialState = generateOneDeterministicPerturbationTargets(i, target1.length);
            DataGene[] updatedState = fitnessFunctionFast.updateState(initialState, grn);
            if (i == 0) {
                assertTrue(updatedState[0].getValue() == -1);
                assertTrue(updatedState[1].getValue() == -1);
                assertTrue(updatedState[2].getValue() == 1);
            }
            else {
                assertTrue(updatedState[0].getValue() == -1);
                assertTrue(updatedState[1].getValue() == -1);
                assertTrue(updatedState[2].getValue() == -1);
            }
        }
    }

    public static List<EdgeGene> getEdgeGeneList(Integer[] grnArray) {
        List<EdgeGene> grnList = new ArrayList<>();
        for (Integer e : grnArray) {
            grnList.add(new EdgeGene(e));
        }
        return grnList;
    }

    public static DataGene[][] generateDeterministicPerturbationTargets(final int perturbations) {
        DataGene[][] dataGenes = new DataGene[perturbations][target1.length];
        for (int i=0; i<perturbations; i++) {
            dataGenes[i] = generateOneDeterministicPerturbationTargets(i, target1.length);
        }
        return dataGenes;
    }

    public static DataGene[] generateOneDeterministicPerturbationTargets(final int index, final int len) {
        DataGene[] dataGenes = new DataGene[len];
        for (int i=0; i<len; i++) {
            if (index % 2 == 1) {
                dataGenes[i] = new DataGene(1);
            } else {
                dataGenes[i] = new DataGene(-1);
            }
        }
        return dataGenes;
    }

    public static SimpleMaterial generateGRN() {
        Integer[] grnArray = {
                1, 0, 0,
                0, 1, -1,
                -1, -1, -1
        };
        List<EdgeGene> grnList = getEdgeGeneList(grnArray);
        return new SimpleMaterial(grnList);
    }
}
