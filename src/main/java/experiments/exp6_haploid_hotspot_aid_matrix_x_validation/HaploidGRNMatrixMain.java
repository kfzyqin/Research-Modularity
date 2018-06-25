package experiments.exp6_haploid_hotspot_aid_matrix_x_validation;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHaploidFrame;
import ga.frame.states.SimpleHaploidState;
import ga.frame.states.State;
import ga.operations.fitnessFunctions.*;
import ga.operations.initializers.HaploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.GRNRandomEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.*;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleProportionalSelector;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;
import ga.others.ModularityPathAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This file belongs to the experiments to compare crossover at GRN diagonals crossover
 * specified at Larson's paper.
 *
 * Created by Zhenyue Qin on 23/06/2017.
 * The Australian National University.
 */
public class HaploidGRNMatrixMain {
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
    private static final int maxCycle = 30;
    private static final int edgeSize = 20;
    private static final int perturbations = 500;
    private static final double perturbationRate = 0.15;

    /* Parameters of the GA */
    private static final double geneMutationRate = 0.05;
    private static final int numElites = 10;
    private static final int populationSize = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.9;

    private static final int maxGen = 2000;
    private static final List<Integer> thresholds = Arrays.asList(0, 500); // when to switch targets
    private static final double alpha = 0.75;
    private static final int[] perturbationSizes = {1, 2};
    private static final int perturbationCycleSize = 75;

    /* Settings for text outputs */
    private static final String summaryFileName = "Haploid-GRN-Matrix.txt";
    private static final String csvFileName = "Haploid-GRN-Matrix.csv";
    private static final String outputDirectory = "test-only";
    private static final String mainFileName = "HaploidGRNMatrixMain.java";
    private static final String allPerturbationsName = "Haploid-GRN-Matrix.per";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    /* Settings for graph outputs */
    private static final String plotTitle = "Haploid GRN Matrix";
    private static final String plotFileName = "Haploid-GRN-Matrix.png";

    private static final double stride = 0.1;

    public static void main(String[] args) throws IOException, InterruptedException {
//        int[][] targets = {target1, target2, target3, target4, target5, target6, target7};
        int[][] targets = {target1, target2};

        /* Fitness function */
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);
//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        /* It is not necessary to write an initializer, but doing so is convenient to
        repeat the experiment using different parameter */
        HaploidGRNInitializer initializer = new HaploidGRNInitializer(populationSize, target1.length, edgeSize);

        /* Population */
        Population<SimpleHaploid> population = initializer.initialize();

        /* Mutator for chromosomes */
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        /* Selector for reproduction */
        Selector<SimpleHaploid> selector = new SimpleTournamentSelector<>(tournamentSize);
//        Selector<SimpleHaploid> selector = new SimpleProportionalSelector<>();

//        /* Selector for elites */
//        PriorOperator<SimpleHaploid> priorOperator = new SimpleElitismOperator<>(numElites);

        /* PostOperator is required to fill up the vacancy */
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(
                new SimpleTournamentScheme(3));

        /* Reproducer for reproduction */
        Reproducer<SimpleHaploid> reproducer = new GRNHaploidNoXReproducer();

        /* Statistics for keeping track the performance in generations */
        DetailedStatistics<SimpleHaploid> statistics = new DetailedStatistics<>();

        /* Set output paths */
        String outputDirectoryPath = outputDirectory + "/" + dateFormat.format(date);
        statistics.setDirectory(outputDirectoryPath);
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/exp6_haploid_hotspot_aid_matrix_x_validation/" + mainFileName);

        /* The state of an GA */
        State<SimpleHaploid> state = new SimpleHaploidState<>(
                population, fitnessFunction, mutator, reproducer, selector, 2, reproductionRate);
        state.record(statistics); // record the initial state of an population

        /* The frame of an GA to change states */
        Frame<SimpleHaploid> frame = new SimpleHaploidFrame<>(state,postOperator,statistics);

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
