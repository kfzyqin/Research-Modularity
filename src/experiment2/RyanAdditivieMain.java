package experiment2;

import ga.collections.Population;
import ga.collections.SimpleElitesStatistics;
import ga.collections.Statistics;
import ga.components.chromosome.SimpleDiploid;
import ga.components.materials.SimpleDNA;
import ga.frame.GAFrame;
import ga.frame.GAState;
import ga.frame.SimpleGAFrame;
import ga.frame.SimpleGAState;
import ga.operations.dynamicHandler.DynamicHandler;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.initializers.Initializer;
import ga.operations.mutator.ChromosomeMutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.recombinator.Recombinator;
import ga.operations.recombinator.SimpleDiploidRecombinator;
import ga.operations.selectors.Selector;
import ga.operations.selectors.SimpleTournamentScheme;
import ga.operations.selectors.SimpleTournamentSelector;

/**
 * Created by david on 11/09/16.
 */
public class RyanAdditivieMain {
    private static final int size = 100;
    private static final int maxGen = 2000;
    private static final int numElites = 1;
    private static final int length = 100;
    private static final double mutationRate = 0.05;
    private static final double recombinationRate = 0.6;
    private static final int cycleLength = 100;
    private static final double dominanceChangeThreshold = 0.05;
    private static final int tournamentSize = 2;
    private static final double selectivePressure = 0.7;
    // private static final double epsilon = .5;
    private static final double rho = 0.1;
    // private static final double maxFit = 32;
    private static final String outfile = "RyanAdditive.out";

    public static void main(String[] args) {
        FitnessFunction<SimpleDNA> fitnessFunction = new DynamicOneMax(length, rho);
        Initializer<SimpleDiploid> initializer = new RyanAdditiveInitializer(length, size);
        Population<SimpleDiploid> population = initializer.initialize();
        Recombinator<SimpleDiploid> recombinator = new SimpleDiploidRecombinator();
        ChromosomeMutator<SimpleDiploid> chromosomeMutator = new RyanAdditiveChromosomeMutator(mutationRate);
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);
        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));
        PriorOperator<SimpleDiploid> elitismOperator = new SimpleElitismOperator<>(numElites);
        DynamicHandler<SimpleDiploid> handler = new RyanAdditiveDominanceChange(dominanceChangeThreshold, cycleLength);
        Statistics<SimpleDiploid> statistics = new SimpleElitesStatistics<>();

        GAState<SimpleDiploid> state = new SimpleGAState<>(population, fitnessFunction, chromosomeMutator, recombinator, selector, 2, recombinationRate);
        state.record(statistics);
        GAFrame<SimpleDiploid> frame = new SimpleGAFrame<>(state, fillingOperator, statistics, handler);
        frame.setPriorOperator(elitismOperator);
        statistics.print(0);

        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
        }
        statistics.save(outfile);


    }
}
