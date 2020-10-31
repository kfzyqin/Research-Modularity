package tests;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.operations.initializers.PreFixedIndividualInitializer;
import ga.operations.selectionOperators.selectors.ExtendedTournamentSelector;
import ga.operations.selectionOperators.selectors.Selector;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Rouyi Jin on 30/10/20.
 * The Australian National University.
 */
public class ExtendedTournamentSelectionTest {
    private static final int[] target1 = {
            1, -1, 1
    };

    private static final int[] target2 = {
            1, -1, -1
    };

    int[][] targets = {target1, target2};
    private static List<Integer> thresholds = Arrays.asList(0, 2);
    private static double k = 0.5;
    private static int maxPerturbation = 2;
    private static List<Integer> compulsory_thrsholds = Arrays.asList(0, 2);

    PreFixedIndividualInitializer initializer = new PreFixedIndividualInitializer(2, target1.length, 6);
    Population<SimpleHaploid> population = initializer.initialize();
    Individual individual1 = population.getIndividualsView().get(0);

    @Test
    public void testExtendedTournamentSelection() {
        Selector<SimpleHaploid> tourSelector = new ExtendedTournamentSelector<>(2, targets, 5, thresholds, k, maxPerturbation, compulsory_thrsholds);
        tourSelector.setSelectionData(population.getIndividualsView());
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(46,47)) == 1);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(43,44)) == 1);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(40,41)) == 0);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(37,38)) == 0);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(34,35)) == 1);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(31,32)) == 1);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(28,29)) == 1);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(25,26)) == 0);
        assertTrue(Integer.parseInt(tourSelector.select(1).toString().substring(22,23)) == 1);
    }

}
