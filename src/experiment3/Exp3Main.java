package experiment3;

import ga.collections.GenderPopulation;
import ga.collections.SimpleGenderElitesStatistics;
import ga.components.chromosome.SimpleGenderDiploid;
import ga.frame.GAFrame;
import ga.frame.GenderGAState;
import ga.frame.SimpleGenderGAFrame;
import ga.frame.SimpleGenderGAState;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.mutator.BinaryGeneChromosomeMutator;
import ga.operations.mutator.ChromosomeMutator;
import ga.operations.mutator.HotspotMutator;
import ga.operations.mutator.SimpleDiscreteExpHotspotMutator;
import ga.operations.postOperators.SimpleFillingOperator;
import ga.operations.priorOperators.SimpleGenderElitismOperator;
import ga.operations.recombinator.CoupleRecombinator;
import ga.operations.recombinator.SimpleGenderRecombinator;
import ga.operations.selectors.CoupleSelector;
import ga.operations.selectors.SimpleTournamentCoupleSelector;
import ga.operations.selectors.SimpleTournamentScheme;

/**
 * Created by david on 30/09/16.
 */
public class Exp3Main {

    private static final int target = 0xf71b72e5;
    private static final int size = 200;
    private static final int maxGen = 2000;
    private static final int numElites = 10;
    private static final int tournamentSize = 2;
    private static final double selectivePressure = 0.7;
    private static final double mutationRate = 0.05;
    private static final double crossoverRate = .8;
    private static final double epsilon = .5;
    private static final int maxFit = 32;
    private static final String outfile = "Exp3.out";

    public static void main(String[] args) {
        // Fitness Function
        FitnessFunction fitnessFunction = new Exp3Fitness();
        // Initializer
        Exp3Initializer initializer = new Exp3Initializer(size, maxFit);
        // Population
        GenderPopulation<SimpleGenderDiploid<Integer>> population = initializer.initialize();
        // Mutator
        ChromosomeMutator<SimpleGenderDiploid<Integer>> mutator = new BinaryGeneChromosomeMutator<>(mutationRate);
        // Selector for reproduction
        CoupleSelector<SimpleGenderDiploid<Integer>> selector = new SimpleTournamentCoupleSelector<>(tournamentSize, selectivePressure);
        // Elitism operator for gender-based diploids
        SimpleGenderElitismOperator<SimpleGenderDiploid<Integer>> priorOperator = new SimpleGenderElitismOperator<>(numElites);
        // Simple filling operator
        SimpleFillingOperator<SimpleGenderDiploid<Integer>> postOperator = new SimpleFillingOperator<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));
        // Statistics
        SimpleGenderElitesStatistics<SimpleGenderDiploid<Integer>> statistics = new SimpleGenderElitesStatistics<>(maxGen);
        // Recombinator
        CoupleRecombinator<SimpleGenderDiploid<Integer>> recombinator = new SimpleGenderRecombinator(1);
        // Hotspot mutator
        HotspotMutator<Integer> hotspotMutator = new SimpleDiscreteExpHotspotMutator(1);

        GenderGAState<SimpleGenderDiploid<Integer>,Integer> state = new SimpleGenderGAState<>(
                population, fitnessFunction, mutator, recombinator, selector, hotspotMutator, crossoverRate);

        GAFrame<SimpleGenderDiploid<Integer>> frame = new SimpleGenderGAFrame<>(state, postOperator, statistics);

        frame.setPriorOperator(priorOperator);

        state.record(statistics);

        statistics.print(0);

        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon) break;
        }

        statistics.save(outfile);
    }
}
