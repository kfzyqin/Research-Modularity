package examples.experiment4;

import ga.frame.Frame;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.SimpleFillingOperator;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import genderGAWithHotspots.collections.GenderPopulation;
import genderGAWithHotspots.collections.SimpleGenderElitesStatistics;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;
import genderGAWithHotspots.frame.GenderState;
import genderGAWithHotspots.frame.SimpleGenderFrame;
import genderGAWithHotspots.frame.SimpleGenderState;
import genderGAWithHotspots.operations.hotspotMutators.HotspotMutator;
import genderGAWithHotspots.operations.hotspotMutators.SimpleDiscreteExpHotspotMutator;
import genderGAWithHotspots.operations.priorOperators.SimpleGenderElitismOperator;
import genderGAWithHotspots.operations.reproducers.CoupleReproducer;
import genderGAWithHotspots.operations.reproducers.SimpleGenderDiploidReproducer;
import genderGAWithHotspots.operations.selectors.CoupleSelector;
import genderGAWithHotspots.operations.selectors.SimpleTournamentCoupleSelector;

import java.io.IOException;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GenderDiploidGRNMain {

    private static final int[] target = {-1, 1, -1, 1};
    private static final int maxCycle = 30;
    private static final int edgeSize = 3;

    private static final int size = 200;
    private static final int maxGen = 1000;
    private static final int numElites = 10;
    private static final int tournamentSize = 2;
    private static final double selectivePressure = 0.7;
    private static final double mutationRate = 0.05;
    private static final double crossoverRate = .8;
    private static final double epsilon = .5;
    private static final int maxFit = 2000;
    private static final String outfile = "Exp4.out";

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle);

        // Initializer
        GenderDiploidGRNInitializer initializer = new GenderDiploidGRNInitializer(size, target, edgeSize);

        // Population
        GenderPopulation<SimpleGenderDiploid<Integer>> population = initializer.initialize();

        // Mutator
        Mutator<SimpleGenderDiploid<Integer>> mutator = new GRNEdgeMutator<>(mutationRate);

        // Selector for reproduction
        CoupleSelector<SimpleGenderDiploid<Integer>> selector = new SimpleTournamentCoupleSelector<>(tournamentSize, selectivePressure);

        // Elitism operator for gender-based diploids
        SimpleGenderElitismOperator<SimpleGenderDiploid<Integer>> priorOperator = new SimpleGenderElitismOperator<>(numElites);

        // Simple filling operator
        SimpleFillingOperator<SimpleGenderDiploid<Integer>> postOperator = new SimpleFillingOperator<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));

        // Statistics
        SimpleGenderElitesStatistics<SimpleGenderDiploid<Integer>> statistics = new SimpleGenderElitesStatistics<>(maxGen);

        // Reproducer
        CoupleReproducer<SimpleGenderDiploid<Integer>> recombinator = new SimpleGenderDiploidReproducer(1);

        // Hotspot mutators
        HotspotMutator<Integer> hotspotMutator = new SimpleDiscreteExpHotspotMutator(1);

        GenderState<SimpleGenderDiploid<Integer>,Integer> state = new SimpleGenderState<>(
                population, fitnessFunction, mutator, recombinator, selector, hotspotMutator, crossoverRate);

        Frame<SimpleGenderDiploid<Integer>> frame = new SimpleGenderFrame<>(state, postOperator, statistics);

        frame.setPriorOperator(priorOperator);

        state.record(statistics);
        statistics.print(0);

        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon) break;
        }
        statistics.save(outfile);
        statistics.generateCSVFile("Soto-6-Targets.csv");
    }
}
