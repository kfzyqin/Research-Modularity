package tests;

import ga.others.GeneralMethods;
import org.junit.Test;

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
        System.out.println(GeneralMethods.setMaxPerturbation(target2, 0.05));
        assertTrue(GeneralMethods.setMaxPerturbation(target1, 0.05) == 3);
        assertTrue(GeneralMethods.setMaxPerturbation(target2, 0.05) == 4);
    }

}
