package experiments.experiment1;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHaploidFrame;
import ga.frame.states.SimpleState;
import ga.frame.states.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.initializers.HaploidGRNInitializer;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleHaploidMatrixHorizontalSplitReproducer;
import ga.operations.reproducers.SimpleHaploidMatrixReproducer;
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
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class HaploidGRN2Target10MatrixLarsonHorizontalMain {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;

    private static final double perturbationRate = 0.15;
    private static final double geneMutationRate = 0.005;
    private static final int numElites = 10;

    private static final int perturbationCycleSize = 20;

    private static final int size = 100;
    private static final int tournamentSize = 3;

    private static final int maxGen = 1050;
    private static final double reproductionRate = 0.9;

    private static final double epsilon = .5;
    private static final double maxFit = 501;

    private static final String summaryFileName = "Haploid-GRN-2-Target-10-Matrix-Larson-Horizontal.txt";
    private static final String csvFileName = "Haploid-GRN-2-Target-10-Matrix-Larson-Horizontal.csv";
    private static final String outputDirectory = "haploid-grn-2-target-10-matrix-larson-horizontal";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Haploid GRN 2 Target 10 Matrix Larson Horizontal";
    private static final String plotFileName = "Haploid-GRN-2-Target-10-Matrix-Larson-Horizontal.png";
    private static final String mainFileName = "HaploidGRN2Target10MatrixLarsonHorizontalMain.java";

    private static final List<Integer> thresholds = Arrays.asList(0, 300);

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2};

        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(
                targets, maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize);

        // It is not necessary to write an initializer, but doing so is convenient to repeat the experiment
        // using different parameter.
        Initializer<SimpleHaploid> initializer = new HaploidGRNInitializer(size, target1.length, edgeSize);

        // Population
        Population<SimpleHaploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        // Selector<SimpleHaploid> selector = new SimpleProportionalSelector<>();
        Selector<SimpleHaploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleHaploid> priorOperator = new SimpleElitismOperator<>(numElites);

        // PostOperator is required to fill up the vacancy.
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        // Statistics for keeping track the performance in generations
        DetailedStatistics<SimpleHaploid> statistics = new DetailedStatistics<>();
        // Reproducer for reproduction
        Reproducer<SimpleHaploid> reproducer = new SimpleHaploidMatrixHorizontalSplitReproducer(target1.length);

        State<SimpleHaploid> state = new SimpleState<>(
                population, fitnessFunction, mutator, reproducer, selector, 2, reproductionRate);
        state.record(statistics);
        Frame<SimpleHaploid> frame = new SimpleHaploidFrame<>(state,postOperator,statistics, priorOperator);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/experiment1/" + mainFileName);

        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon)
                break;
        }
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);
    }
}
