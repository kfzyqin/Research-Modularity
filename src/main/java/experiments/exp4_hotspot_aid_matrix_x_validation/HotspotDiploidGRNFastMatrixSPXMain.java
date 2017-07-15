package experiments.exp4_hotspot_aid_matrix_x_validation;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHotspotDiploidMultipleTargetFrame;
import ga.frame.states.SimpleHotspotDiploidMultipleTargetState;
import ga.frame.states.State;
import ga.operations.dominanceMapMutators.DiploidDominanceMapMutator;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.hotspotMutators.RandomHotspotMutator;
import ga.operations.initializers.HotspotDiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.GRNHotspotDiploidEvolvedSPXMatrixReproducer;
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
 * Created by Zhenyue Qin (秦震岳) on 25/6/17.
 * The Australian National University.
 */
public class HotspotDiploidGRNFastMatrixSPXMain {
    /* The three targets that the GA evolve towards */
    private static final int[] target1 = {
            1, -1, 1, -1, 1, -1, 1, 1,
            -1, 1, -1, 1, -1, 1, -1, 1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1, -1, 1, 1,
            1, -1, 1, -1, 1, -1, 1, -1
    };

    /* Parameters of the GRN */
    private static final int maxCycle = 20;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final double geneMutationRate = 0.025;
    private static final int perturbationCycleSize = 100;
    private static final double dominanceMutationRate = 0.005;
    private static final double perturbationRate = 0.15;

    /* Parameters of the GA */
    private static final int hotspotSize = 3;
    private static final double hotspotMutationRate = 0.01;
    private static final int numElites = 20;
    private static final int populationSize = 50;
    private static final int tournamentSize = 5;
    private static final double reproductionRate = 0.8;
    private static final int maxGen = 1050;
    private static final List<Integer> thresholds = Arrays.asList(0, 300);

    /* Settings for text outputs */
    private static final String summaryFileName = "Hotspot-Diploid-GRN-3-Target-10-Matrix-Evolved-SPX.txt";
    private static final String csvFileName = "Hotspot-Diploid-GRN-3-Target-10-Matrix-Evolved-SPX.csv";
    private static final String outputDirectory = "hotspot-diploid-grn-3-target-10-matrix-evolved-spx";
    private static final String mainFileName = "HotspotDiploidGRNFastMatrixSPXMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    /* Settings for graph outputs */
    private static final String plotTitle = "Hotspot Diploid GRN 3 Targets 10 Matrix Evolved SPX";
    private static final String plotFileName = "Hotspot Diploid-GRN-3-Target-10-Matrix-Evolved-SPX.png";


    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2};

        /* Fitness Function */
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(targets,
                maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize);

        /* It is not necessary to write an initializer, but doing so is convenient to
        repeat the experiment using different parameter */
        HotspotDiploidGRNInitializer initializer =
                new HotspotDiploidGRNInitializer(populationSize, target1.length, edgeSize, hotspotSize);

        /* Population */
        Population<SimpleHotspotDiploid> population = initializer.initializeWithMatrixHotspot();

        /* Mutator for chromosomes */
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        /* Selector for reproduction */
        Selector<SimpleHotspotDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        /* Selector for elites */
        PriorOperator<SimpleHotspotDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        /* PostOperator is required to fill up the vacancy */
        PostOperator<SimpleHotspotDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(
                new SimpleTournamentScheme(tournamentSize));

        /* Reproducer for reproduction */
        Reproducer<SimpleHotspotDiploid> reproducer = new
                GRNHotspotDiploidEvolvedSPXMatrixReproducer(0.5, target1.length);

        /* Statistics for keeping track the performance in generations */
        DetailedStatistics<SimpleHotspotDiploid> statistics = new DetailedStatistics<>();

        /* Dominance expression map mutator */
        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        /* Hotspot mutator */
        HotspotMutator hotspotMutator = new RandomHotspotMutator(hotspotMutationRate);

        /* The state of an GA */
        State<SimpleHotspotDiploid> state = new SimpleHotspotDiploidMultipleTargetState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator, hotspotMutator);
        state.record(statistics);

        /* The frame of an GA to change states */
        Frame<SimpleHotspotDiploid> frame = new SimpleHotspotDiploidMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);

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
