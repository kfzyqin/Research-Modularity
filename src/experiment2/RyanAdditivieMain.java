package experiment2;

import ga.collections.Population;
import ga.collections.SimpleElitesStatistics;
import ga.collections.Statistics;
import ga.components.chromosome.SequentialDiploid;
import ga.components.materials.DNAStrand;
import ga.frame.GAFrame;
import ga.frame.GAState;
import ga.frame.SimpleGAFrame;
import ga.frame.SimpleGAState;
import ga.operations.dynamicHandler.DynamicHandler;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.recombiners.Recombiner;
import ga.operations.recombiners.SimpleSequentialDiploidRecombiner;
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
        Fitness<DNAStrand> fitness = new DynamicOneMax(length, rho);
        Initializer<SequentialDiploid> initializer = new RyanAdditiveInitializer(length, size);
        Population<SequentialDiploid> population = initializer.initialize();
        Recombiner<SequentialDiploid> recombiner = new SimpleSequentialDiploidRecombiner();
        Mutator<SequentialDiploid> mutator = new RyanAdditiveMutator(mutationRate);
        Selector<SequentialDiploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);
        PostOperator<SequentialDiploid> fillingOperator = new SimpleFillingOperator<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));
        PriorOperator<SequentialDiploid> elitismOperator = new SimpleElitismOperator<>(numElites);
        DynamicHandler<SequentialDiploid> handler = new RyanAdditiveDominanceChange(dominanceChangeThreshold, cycleLength);
        Statistics<SequentialDiploid> statistics = new SimpleElitesStatistics<>();

        GAState<SequentialDiploid> state = new SimpleGAState<>(population, fitness, mutator, recombiner, selector, 2, recombinationRate);
        state.record(statistics);
        GAFrame<SequentialDiploid> frame = new SimpleGAFrame<>(state, fillingOperator, statistics, handler);
        frame.setPriorOperator(elitismOperator);
        statistics.print(0);

        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
        }
        statistics.save(outfile);


    }
}
