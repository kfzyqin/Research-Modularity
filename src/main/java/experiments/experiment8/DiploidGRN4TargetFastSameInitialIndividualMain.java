package experiments.experiment8;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleDiploidMultipleTargetFrame;
import ga.frame.states.SimpleDiploidMultipleTargetState;
import ga.frame.states.State;
import ga.operations.dominanceMapMutators.DiploidDominanceMapMutator;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.initializers.DiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleDiploidReproducer;
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
 * Created by zhenyueqin on 25/6/17.
 */
public class DiploidGRN4TargetFastSameInitialIndividualMain {
    private static final int[] target1 = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
    private static final int[] target2 = {1, -1, -1, 1, 1, -1, 1, -1, 1, -1};
    private static final int[] target3 = {1, -1, 1, -1, -1, 1, 1, -1, 1, -1};
    private static final int[] target4 = {1, -1, 1, -1, 1, -1, -1, 1, 1, -1};

    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.001;
    private static final double perturbationRate = 0.15;
    private static final int numElites = 20;

    private static final int perturbationCycleSize = 100;
    private static final int hiddenTargetSize = 7;

    private static final int size = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.8;
    private static final int maxGen = 11000;

    private static final double maxFit = 300;
    private static final double epsilon = .5;

    private static final String summaryFileName = "Diploid-GRN-4-Target-Same-Initial-Individual.sum";
    private static final String csvFileName = "Diploid-GRN-4-Target-Same-Initial-Individual.csv";
    private static final String outputDirectory = "diploid-grn-4-target-same-initial-individual";
    private static final String mainFileName = "DiploidGRN4TargetFastSameInitialIndividualMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Diploid GRN 4 Targets Same Initial Individual Summary";
    private static final String plotFileName = "Diploid-GRN-4-Target-Same-Initial-Individual-Chart.png";

    private static final List<Integer> thresholds = Arrays.asList(0, 300, 1050, 3000);

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2, target3, target4};

        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(targets,
                maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize);

        // Initializer
        DiploidGRNInitializer initializer =
                new DiploidGRNInitializer(size, target1.length, edgeSize);

        // Population
        Population<SimpleDiploid> population = initializer.initializeSameIndividuals();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        Reproducer<SimpleDiploid> reproducer = new SimpleDiploidReproducer();

        DetailedStatistics<SimpleDiploid> statistics = new DetailedStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        State<SimpleDiploid> state = new SimpleDiploidMultipleTargetState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator);

        state.record(statistics);

        Frame<SimpleDiploid> frame = new SimpleDiploidMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/experiment8/" + mainFileName);
        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon) {
                break;
            }
        }
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);

    }
}
