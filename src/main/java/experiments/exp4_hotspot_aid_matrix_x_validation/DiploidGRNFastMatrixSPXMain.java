package experiments.exp4_hotspot_aid_matrix_x_validation;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleDiploidMultipleTargetFrame;
import ga.frame.states.SimpleDiploidState;
import ga.frame.states.State;
import ga.operations.dominanceMapMutators.DiploidDominanceMapMutator;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.initializers.DiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.GRNModularisedEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.GRNDiploidMatrixReproducer;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 25/6/17.
 * The Australian National University.
 */
public class DiploidGRNFastMatrixSPXMain {
    /* The targets that the GA evolve towards */
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            -1, 1, -1, 1, -1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target3 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    /* Parameters of the GRN */
    private static final int maxCycle = 20;
    private static final int edgeSize = 12;
    private static final int perturbations = 500;
    private static final int perturbationCycleSize = 300;
    private static final double dominanceMutationRate = 0.005;
    private static final double perturbationRate = 0.4;

    /* Parameters of the GA */
    private static final double geneMutationRate = 0.01;
    private static final int numElites = 5;
    private static final int populationSize = 25;
    private static final int tournamentSize = 5;
    private static final double reproductionRate = 0.95;
    private static final int maxGen = 1800;
    private static final List<Integer> thresholds = Arrays.asList(0, 300, 1000);
    private static final int moduleIndex = 5;

    /* Settings for text outputs */
    private static final String summaryFileName = "Diploid-GRN-3-Target-10-Matrix-Random-SPX.txt";
    private static final String csvFileName = "Diploid-GRN-3-Target-10-Matrix-Random-SPX.csv";
    private static final String outputDirectory = "diploid-grn-3-target-10-matrix-random-spx-7";
    private static final String mainFileName = "DiploidGRNFastMatrixSPXMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    /* Settings for graph outputs */
    private static final String plotTitle = "Diploid GRN 3 Targets 10 Matrix Random SPX";
    private static final String plotFileName = "Diploid-GRN-3-Target-10-Matrix-Random-SPX.png";

    public static void main(String[] args) throws IOException, ParseException {
        int[][] targets = {target1, target2, target3};

        /* Fitness Function */
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);

        /* It is not necessary to write an initializer, but doing so is convenient to
        repeat the experiment using different parameter */
        DiploidGRNInitializer initializer =
                new DiploidGRNInitializer(populationSize, target1.length, edgeSize);

        /* Population */
        Population<SimpleDiploid> population = initializer.initializeExistingModularizedPopulation(moduleIndex);

        /* Mutator for chromosomes */
        Mutator mutator = new GRNModularisedEdgeMutator(geneMutationRate, moduleIndex);

        /* Selector for reproduction */
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        /* Selector for elites */
        PriorOperator<SimpleDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        /* PostOperator is required to fill up the vacancy */
        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(
                new SimpleTournamentScheme(tournamentSize));

        /* Reproducer for reproduction */
        Reproducer<SimpleDiploid> reproducer = new GRNDiploidMatrixReproducer(0.5, target1.length);

        /* Statistics for keeping track the performance in generations */
        DetailedStatistics<SimpleDiploid> statistics = new DetailedStatistics<>();

        /* Dominance expression map mutator */
        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        /* The state of an GA */
        State<SimpleDiploid> state = new SimpleDiploidState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator);
        state.record(statistics);

        /* The frame of an GA to change states */
        Frame<SimpleDiploid> frame = new SimpleDiploidMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);

        /* Set output paths */
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/exp4_hotspot_aid_matrix_x_validation/" + mainFileName);

        statistics.print(0);
        /* Actual GA evolutions */
        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
        }

        /* Generate output files */
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);
    }
}
