package examples.experiment6;

import examples.experiment4.GRNFitnessFunction;
import examples.experiment4.GenderDiploidGRNInitializer;
import ga.collections.Population;
import ga.collections.SimpleElitesStatistics;
import ga.collections.Statistics;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.Frame;
import ga.frame.SimpleFrame;
import ga.frame.SimpleState;
import ga.frame.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.initializers.DiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleDiploidReproducer;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;
import genderGAWithHotspots.collections.GenderPopulation;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;

import java.io.IOException;

/**
 * Created by Zhenyue Qin on 6/06/2017.
 * The Australian National University.
 */
public class DiploidGRNMain {
    private static final int[] target = {-1, 1, -1, 1, -1, 1, -1, 1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 500;
    private static final double mutationRate = 0.004;

    private static final int size = 100;
    private static final int tournamentSize = 3;
    private static final double selectivePressure = 1.0;
    private static final double reproductionRate = 1.0;
    private static final int maxGen = 2000;

    private static final double maxFit = 500;
    private static final double epsilon = .5;

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle, perturbations);

        // Initializer
        DiploidGRNInitializer initializer = new DiploidGRNInitializer(size, target, edgeSize);

        // Population
        Population<SimpleDiploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(mutationRate);

        // Selector for reproduction
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);

        Reproducer<SimpleDiploid> reproducer = new SimpleDiploidReproducer();

        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));

        SimpleElitesStatistics<SimpleDiploid> statistics = new SimpleElitesStatistics<>();

        State<SimpleDiploid> state = new SimpleState<>(population, fitnessFunction, mutator, reproducer, selector, 2, reproductionRate);

        state.record(statistics);
        Frame<SimpleDiploid> frame = new SimpleFrame<>(state, fillingOperator, statistics);
        statistics.print(0);

        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
//            if (statistics.getOptimum(i) > maxFit - epsilon) {
//                break;
//            }
        }

        statistics.generateCSVFile("Simple-Diploid-10-Targets-GRN-Population-500.csv");
    }
}
