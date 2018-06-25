package tests;

import com.sun.source.tree.AssertTree;
import ga.components.genes.DataGene;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinations;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class GRNFitnessFunctionMultipleTargetsAllCombinationsTest {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };


    private static final int maxCycle = 20;
    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);

    @Test
    public void testPerturbationNumber() {
        int[][] targets = {target1, target2};

        int[] perturbationSizes = {1, 2, 3};
        GRNFitnessFunctionMultipleTargetsAllCombinations testClass = new GRNFitnessFunctionMultipleTargetsAllCombinations(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes
        );

        assertEquals(175, testClass.getPerturbations());
    }

    @Test
    public void testGenerateInitialAttractors() {
        int[][] targets = {target1, target2};

        int[] perturbationSizes = {3};
        GRNFitnessFunctionMultipleTargetsAllCombinations testClass = new GRNFitnessFunctionMultipleTargetsAllCombinations(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes
        );

        DataGene[][] perturbations =  testClass.generateInitialAttractors(testClass.getPerturbations(), 0.15, target1);

        assertEquals(120, perturbations.length);

        for (DataGene[] aPerturbation : perturbations) {
            int differences = 0;
            for (int i = 0; i < aPerturbation.length; i++) {
                if (aPerturbation[i].getValue() != target1[i]) {
                    differences += 1;
                }
            }
            System.out.println(Arrays.toString(aPerturbation));
//                System.out.println(Arrays.toString(target1));
            try {
                assertEquals(differences, perturbationSizes[0]);
            } catch (AssertionError e) {
                System.out.println("Expect differences to be " + perturbationSizes[0] + ", found " + differences);
                throw e;
            }
        }


    }
}
