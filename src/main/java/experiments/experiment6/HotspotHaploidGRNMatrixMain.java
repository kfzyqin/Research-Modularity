package experiments.experiment6;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHotspotHaploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHaploidFrame;
import ga.frame.states.SimpleHotspotHaploidState;
import ga.frame.states.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.hotspotMutators.RandomHotspotMutator;
import ga.operations.initializers.HotspotHaploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.GRNHotspotHaploidMatrixReproducer;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This file belongs to the experiments to compare crossover at GRN diagonals crossover
 * specified at Larson's paper.
 *
 * Created by Zhenyue Qin on 23/06/2017.
 * The Australian National University.
 */
public class HotspotHaploidGRNMatrixMain {
    /* The two targets that the GA evolve towards */
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    /* Parameters of the GRN */
    private static final int maxCycle = 20;
    private static final int edgeSize = 16;
    private static final int perturbations = 300;
    private static final double perturbationRate = 0.15;
    private static final int perturbationCycleSize = 100;

    /* Parameters of the GA */
    private static final double geneMutationRate = 0.0025;
    private static final double hotspotMutationRate = 0.05;
    private static final int numElites = 10;
    private static final int populationSize = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.6;
    private static final int maxGen = 2000;
    private static final List<Integer> thresholds = Arrays.asList(0, 500); // when to switch targets

    /* Settings for text outputs */
    private static final String summaryFileName = "Hotspot-Haploid-GRN-Matrix.txt";
    private static final String csvFileName = "Hotspot-Haploid-GRN-Matrix.csv";
    private static final String outputDirectory = "fixed-haploid-grn-matrix-2-target-10-4";
    private static final String mainFileName = "HotspotHaploidGRNMatrixMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    /* Settings for graph outputs */
    private static final String plotTitle = "Hotspot Haploid GRN 2 Matrix";
    private static final String plotFileName = "Hotspot-Haploid-GRN-Matrix.png";

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2};

        /* Fitness function */
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(
                targets, maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize);

        /* It is not necessary to write an initializer, but doing so is convenient to
        repeat the experiment using different parameter */
        HotspotHaploidGRNInitializer initializer =
                new HotspotHaploidGRNInitializer(populationSize, target1.length, edgeSize, 8);

        /* Population */
        Population<SimpleHotspotHaploid> population = initializer.initializeModularizedPopulation(5);

        /* Mutator for chromosomes */
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        /* Selector for reproduction */
        Selector<SimpleHotspotHaploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        /* Selector for elites */
        PriorOperator<SimpleHotspotHaploid> priorOperator = new SimpleElitismOperator<>(numElites);

        /* PostOperator is required to fill up the vacancy */
        PostOperator<SimpleHotspotHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(
                new SimpleTournamentScheme(tournamentSize));

        /* Reproducer for reproduction */
        Reproducer<SimpleHotspotHaploid> reproducer = new GRNHotspotHaploidMatrixReproducer(target1.length);

        /* Statistics for keeping track the performance in generations */
        DetailedStatistics<SimpleHotspotHaploid> statistics = new DetailedStatistics<>();

        /* Hotspot mutator */
        HotspotMutator hotspotMutator = new RandomHotspotMutator(hotspotMutationRate);

        /* The state of an GA */
        State<SimpleHotspotHaploid> state = new SimpleHotspotHaploidState<>(
                population, fitnessFunction, mutator, reproducer, selector, 2,
                reproductionRate, hotspotMutator);
        state.record(statistics); // record the initial state of an population

        /* The frame of an GA to change states */
        Frame<SimpleHotspotHaploid> frame = new SimpleHaploidFrame<>(state,postOperator,statistics, priorOperator);

        /* Set output paths */

        statistics.print(0); // print the initial state of an population
        /* Actual GA evolutions */
        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
        }

        /* Generate output files */
    }
}
