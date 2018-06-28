package experiments.experiment6;

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
import ga.operations.initializers.DiploidGRNInitializer;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 25/6/17.
 * The Australian National University.
 * This file is solely for debug purposes.
 */
public class DiploidGRN3Target10FastMatrixMain {
    private static final int[] target1 = {
            -1, 1, -1, 1
    };
    private static final int[] target2 = {
            -1, 1, -1, 1
    };
    private static final int[] target3 = {
            -1, 1, -1, 1
    };

    private static final int maxCycle = 5;
    private static final int edgeSize = 4;
    private static final int perturbations = 5;

    private static final double geneMutationRate = 0.005;
    private static final double dominanceMutationRate = 0.002;
    private static final double perturbationRate = 0;
    private static final int numElites = 1;

    private static final int perturbationCycleSize = 5;

    private static final int size = 10;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.9;
    private static final int maxGen = 100;

    private static final double maxFit = 2;
    private static final double epsilon = 0.151;

    private static final String summaryFileName = "Diploid-GRN-3-Target-10-Matrix.txt";
    private static final String csvFileName = "Diploid-GRN-3-Target-10-Matrix.csv";
    private static final String outputDirectory = "diploid-grn-3-target-10-matrix";
    private static final String mainFileName = "DiploidGRN3Target10FastMatrixSPXMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Diploid GRN 3 Targets 10 Matrix";
    private static final String plotFileName = "Diploid-GRN-3-Target-10-Matrix.png";

    private static final List<Integer> thresholds = Arrays.asList(0, 50);

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2, target3};

        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, perturbations, perturbationRate, thresholds);

        // Initializer
        DiploidGRNInitializer initializer =
                new DiploidGRNInitializer(size, target1.length, edgeSize);

        // Population
        Population<SimpleDiploid> population = initializer.initializeModularizedPopulation(2);

        // Mutator for chromosomes
        Mutator mutator = new GRNModularisedEdgeMutator(geneMutationRate,2);

        // Selector for reproduction
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        Reproducer<SimpleDiploid> reproducer = new GRNDiploidMatrixReproducer(0.5, target1.length);

        DetailedStatistics<SimpleDiploid> statistics = new DetailedStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        State<SimpleDiploid> state = new SimpleDiploidState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator);

        state.record(statistics);

        Frame<SimpleDiploid> frame = new SimpleDiploidMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);
//        Frame<SimpleDiploid> frame = new SimpleDiploidMultipleTargetFrame<>(state, fillingOperator, statistics);

        statistics.print(0);
//        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
//        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
//                "/src/main/java/experiments/experiment9/" + mainFileName);
        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if ((statistics.getOptimum(i) > maxFit - epsilon) &&
                    (statistics.getGeneration() > thresholds.get(thresholds.size()-1))) {
                break;
            }
        }
//        statistics.save(summaryFileName);
//        statistics.generateNormalCSVFile(csvFileName);
//        statistics.generatePlot(plotTitle, plotFileName);
    }
}
