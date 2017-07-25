package experiments.exp2_crossover_comparison;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHaploidFrame;
import ga.frame.states.SimpleHaploidState;
import ga.frame.states.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.initializers.HaploidGRNInitializer;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.GRNHaploidMatrixReproducer;
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
public class HaploidGRN2Target15MatrixChinMain {
    /* The two targets that the GA evolve towards */
    private static final int[] target1 = {
            1, -1, 1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1, 1, -1, 1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1, -1, 1,
            1, -1, 1, -1, 1, -1, 1, -1
    };

    /* Parameters of the GRN */
    private static final int maxCycle = 20;
    private static final int edgeSize = 45;
    private static final int perturbations = 300;
    private static final double perturbationRate = 0.15;
    private static final int perturbationCycleSize = 100;

    /* Parameters of the GA */
    private static final double geneMutationRate = 0.005;
    private static final int numElites = 10;
    private static final int populationSize = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.9;
    private static final int maxGen = 1050;
    private static final List<Integer> thresholds = Arrays.asList(0, 300); // when to switch targets

    /* Settings for text outputs */
    private static final String summaryFileName = "Haploid-GRN-2-Target-15-Matrix-Chin.txt";
    private static final String csvFileName = "Haploid-GRN-2-Target-15-Matrix-Chin.csv";
    private static final String outputDirectory = "haploid-grn-2-target-15-matrix-chin";
    private static final String mainFileName = "HaploidGRN2Target15MatrixChinMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    /* Settings for graph outputs */
    private static final String plotTitle = "Haploid GRN 2 Target 15 Matrix Chin";
    private static final String plotFileName = "Haploid-GRN-2-Target-15-Matrix-Chin.png";

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2};

        /* Fitness function */
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);

        /* It is not necessary to write an initializer, but doing so is convenient to
        repeat the experiment using different parameter */
        Initializer<SimpleHaploid> initializer = new HaploidGRNInitializer(populationSize, target1.length, edgeSize);

        /* Population */
        Population<SimpleHaploid> population = initializer.initialize();

        /* Mutator for chromosomes */
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        /* Selector for reproduction */
        Selector<SimpleHaploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        /* Selector for elites */
        PriorOperator<SimpleHaploid> priorOperator = new SimpleElitismOperator<>(numElites);

        /* PostOperator is required to fill up the vacancy */
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(
                new SimpleTournamentScheme(tournamentSize));

        /* Reproducer for reproduction */
        Reproducer<SimpleHaploid> reproducer = new GRNHaploidMatrixReproducer(target1.length);

        /* Statistics for keeping track the performance in generations */
        DetailedStatistics<SimpleHaploid> statistics = new DetailedStatistics<>();

        /* The state of an GA */
        State<SimpleHaploid> state = new SimpleHaploidState<>(
                population, fitnessFunction, mutator, reproducer, selector, 2, reproductionRate);
        state.record(statistics); // record the initial state of an population

        /* The frame of an GA to change states */
        Frame<SimpleHaploid> frame = new SimpleHaploidFrame<>(state,postOperator,statistics, priorOperator);

        /* Set output paths */
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/exp2_crossover_comparison/" + mainFileName);

        statistics.print(0); // print the initial state of an population
        /* Actual GA evolutions */
        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
        }

        /* Generate output files */
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);
    }
}
