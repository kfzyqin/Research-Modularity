package tests;

import ga.components.genes.DataGene;
import ga.others.GeneralMethods;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class GeneralMethodTest {
    @Test
    public void setMinPerturbationTest() {
        int[] target1 = {
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1
        };
        int[] target2 = {
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1
        };
        assertTrue(GeneralMethods.setMinPerturbation(target1, 100) == 3);
        assertTrue(GeneralMethods.setMinPerturbation(target2, 100) == 2);
    }

    @Test
    public void normalisedPerturbationWeightTest() {
//        System.out.println(GeneralMethods.normalisedPerturbationWeight(15, 3, 2, 4));
        assertTrue(GeneralMethods.normalisedPerturbationWeight(15, 3, 2, 4)- 0.35241720893 < 0.0001 );
    }

    @Test
    public void generateImportanceSamplingSizeTest() {
        assertTrue(GeneralMethods.generateImportanceSamplingSize(15, 3, 2, 4) == 353);
    }

    @Test
    public void setMaxPerturbationTest() {
        int[] target1 = {
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1
        };
        int[] target2 = {
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1
        };
        assertTrue(GeneralMethods.setMaxPerturbation(target1, 0.05) == 3);
        assertTrue(GeneralMethods.setMaxPerturbation(target2, 0.05) == 4);
    }
    @Test
    public void testBest() {
        List<Double> doubles = Arrays.asList(0.2, 0.1, 0.4, 0.2, 0.3);
        TestCase.assertTrue(GeneralMethods.best(doubles) == 0.4);

    }

    @Test
    public void testkproportion() {
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0);
        double x = GeneralMethods.kproportion(0.1, doubles);
        double y = GeneralMethods.kproportion(0.5, doubles);
        TestCase.assertTrue(x == 9);
        TestCase.assertTrue(y == 5);

    }

    private static final int[] target1 = {
            1, -1, 1
    };


    @Test
    public void testGenerateEveryPerturbationAttractors() {
        Map<Integer, DataGene[][]> calculated = GeneralMethods.generateEveryPerturbationAttractors(target1,3);
        TestCase.assertTrue(calculated.containsKey(0));
        TestCase.assertTrue(calculated.containsKey(1));
        TestCase.assertTrue(calculated.containsKey(2));
        TestCase.assertTrue(calculated.containsKey(3));
        TestCase.assertTrue(calculated.get(0).length == 1);
        TestCase.assertTrue(calculated.get(1).length == 3);
        TestCase.assertTrue(calculated.get(0)[0][1].getValue() == -1);
        TestCase.assertTrue(calculated.get(3)[0][1].getValue() == 1);

    }

}
