package tests;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.initializers.PreFixedIndividualInitializer;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class IndividualFunctionTest<M extends Material, P extends Material> {

    private static final int[] target1 = {
            1, -1, 1
    };

    private static final int[] target2 = {
            1, -1, -1
    };

    private static final int[] target3 = {
            -1, -1, -1
    };



    PreFixedIndividualInitializer initializer = new PreFixedIndividualInitializer(1, target1.length, 5);
    Population<SimpleHaploid> population = initializer.initialize();
    Individual individual1 = population.getIndividualsView().get(0);

    DataGene[] target = new DataGene[3];
    DataGene[] current = new DataGene[3];
    DataGene[] current2 = new DataGene[3];


    @Test
    public void testCanRegulateToTarget() throws Exception {
        current[0] = new DataGene(1);
        current[1] = new DataGene(1);
        current[2] = new DataGene(1);
        for (int i = 0; i < target3.length; i++) {
            current2[i] = new DataGene(target3[i]);
        }
        current2[0] = new DataGene(-1);
        current2[1] = new DataGene(1);
        current2[2] = new DataGene(-1);
        assertTrue(individual1.canRegulateToTarget(target3, current2, 4));
        assertFalse(individual1.canRegulateToTarget(target1, current, 2));

    }

    @Test
    public void testCanAchieveAttractorAndGetAttractor() throws Exception {
        DataGene[] current = new DataGene[3];
        current[0] = new DataGene(1);
        current[1] = new DataGene(1);
        current[2] = new DataGene(-1);
        assertTrue(individual1.canAchieveAttractor(current, 5));

        DataGene[] attractor = new DataGene[3];
        attractor[0] = new DataGene(-1);
        attractor[1] = new DataGene(-1);
        attractor[2] = new DataGene(-1);
        assertTrue(individual1.equalDataGeneArray(attractor, individual1.getAttractor(current, 5)));

    }

    @Test
    public void testUpdate() {
        for (int i = 0; i < target1.length; i++) {
            current[i] = new DataGene(target1[i]);
        }

        DataGene[] updated = individual1.update(current, generateGRN());
        assertTrue(updated[0].getValue() == -1);
        assertTrue(updated[1].getValue() == -1);
        assertTrue(updated[2].getValue() == -1);

    }

    public static List<EdgeGene> getEdgeGeneList(Integer[] grnArray) {
        List<EdgeGene> grnList = new ArrayList<>();
        for (Integer e : grnArray) {
            grnList.add(new EdgeGene(e));
        }
        return grnList;
    }


    public static SimpleMaterial generateLargeGRN() {
        Integer[] grnArray = {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0,
                -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, -1, 0, 0, 0, -1, 1, -1, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -1, 0, 0, 0, 0, -1, 0, 0, 0,
                0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        List<EdgeGene> grnList = getEdgeGeneList(grnArray);
        return new SimpleMaterial(grnList);
    }



    public static SimpleMaterial generateGRNSecond() {
        Integer[] grnArray = {
                1, 0, 1,
                1, 1, 0,
                0, 1, 1
        };
        List<EdgeGene> grnList = getEdgeGeneList(grnArray);
        return new SimpleMaterial(grnList);
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

    @Test
    public void testEqualDataGeneArray() {

        for (int i = 0; i < target1.length; i++) {
            current[i] = new DataGene(target1[i]);
        }

        target[0] = new DataGene(-1);
        target[1] = new DataGene(-1);
        target[2] = new DataGene(1);

        assertTrue(individual1.equalDataGeneArray(target, target));
        assertFalse(individual1.equalDataGeneArray(target, current));
    }



}
