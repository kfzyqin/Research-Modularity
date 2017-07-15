package tests;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Diploid;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Zhenyue Qin (秦震岳) on 15/7/17.
 * The Australian National University.
 */
public class TournamentSelectTest {

    static class TmpDiploid extends Diploid {
        public double itsFitness;

        public TmpDiploid(double itsFitness) {
            super(tmpMaterial, tmpMaterial, tmpExpressionMap);
            this.itsFitness = itsFitness;
        }

        @Override
        public Object copy() {
            return null;
        }
    }

    public static SimpleTournamentScheme tournamentScheme = new SimpleTournamentScheme(7);
    public static SimpleTournamentSelector<TmpDiploid> tournamentSelector = new SimpleTournamentSelector<>(6);
    public static Population<TmpDiploid> population = new Population<>(7);
    public static ExpressionMap tmpExpressionMap = new ExpressionMap() {
        @Override
        public Material map(List materials) {
            return null;
        }

        @Override
        public ExpressionMap copy() {
            return null;
        }
    };
    public static SimpleMaterial tmpMaterial = new SimpleMaterial(new ArrayList<>());

    @Test
    public void testSelect() {
        population.setMode(PopulationMode.PRIOR);
        population.addCandidate(generateIndividual(7.0));
        population.addCandidate(generateIndividual(6.0));
        population.addCandidate(generateIndividual(5.0));
        population.addCandidate(generateIndividual(4.0));
        population.addCandidate(generateIndividual(3.0));
        population.addCandidate(generateIndividual(2.0));
        population.addCandidate(generateIndividual(1.0));
        population.nextGeneration();

        List<Double> fitnessList = Arrays.asList(7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0);
        assertTrue(tournamentScheme.select(fitnessList) == 0);

        tournamentSelector.setSelectionData(population.getIndividualsView());
        assertTrue(tournamentSelector.select(2).get(0).itsFitness == 7.0);
        assertTrue(tournamentSelector.select(2).get(1).itsFitness == 6.0);
    }

    public static Individual<TmpDiploid> generateIndividual(double fitness) {
        Individual<TmpDiploid> anIndividual = new Individual(new TmpDiploid(fitness));
        anIndividual.evaluate(generateFitnessFunction(fitness), true);
        return anIndividual;
    }

    public static FitnessFunction generateFitnessFunction(double fitness) {
        return new FitnessFunction() {

            @Override
            public double evaluate(Material phenotype) {
                return fitness;
            }

            @Override
            public void update() {

            }
        };
    }
}
