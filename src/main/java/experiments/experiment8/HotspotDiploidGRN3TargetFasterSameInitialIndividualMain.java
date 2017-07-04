package experiments.experiment8;

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
import ga.operations.reproducers.SimpleHotspotDiploidMatrixReproducer;
import ga.operations.reproducers.SimpleHotspotDiploidReproducer;
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
public class HotspotDiploidGRN3TargetFasterSameInitialIndividualMain {
    private static final int[] target1 = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
    private static final int[] target2 = {1, -1, 1, -1, 1, -1, 1, 1, -1, 1};
    private static final int[] target3 = {1, -1, 1, -1, -1, 1, -1, 1, -1, 1};

    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final int hotspotSize = 9;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.001;
    private static final double hotspotMutationRate = 0.0005;
    private static final double perturbationRate = 0.15;
    private static final int numElites = 20;

    private static final int perturbationCycleSize = 100;
    private static final int hiddenTargetSize = 7;

    private static final int size = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.8;
    private static final int maxGen = 3000;

    private static final double maxFit = 300;
    private static final double epsilon = .5;

    private static final String summaryFileName = "Hotspot-Diploid-GRN-3-Target-Same-Initial-Individual.sum";
    private static final String csvFileName = "Hotspot-Diploid-GRN-3-Target-Same-Initial-Individual.csv";
    private static final String outputDirectory = "hotspot-diploid-grn-3-target-same-initial-individual";
    private static final String mainFileName = "HotspotDiploidGRN3TargetFasterSameInitialIndividualMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Hotspot Diploid GRN 3 Targets Same Initial Individual Summary";
    private static final String plotFileName = "Hotspot-Diploid-GRN-3-Target-Same-Initial-Individual-Chart.png";

    private static final List<Integer> thresholds = Arrays.asList(0, 300, 1050);

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2, target3};

        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(targets,
                maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize);

        // Initializer
        HotspotDiploidGRNInitializer initializer =
                new HotspotDiploidGRNInitializer(size, target1.length, edgeSize, hotspotSize);

        // Population
        Population<SimpleHotspotDiploid> population = initializer.initializeSameIndividuals();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<SimpleHotspotDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleHotspotDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        PostOperator<SimpleHotspotDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        Reproducer<SimpleHotspotDiploid> reproducer = new SimpleHotspotDiploidMatrixReproducer(target1.length);

        DetailedStatistics<SimpleHotspotDiploid> statistics = new DetailedStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        HotspotMutator hotspotMutator = new RandomHotspotMutator(hotspotMutationRate);

        State<SimpleHotspotDiploid> state = new SimpleHotspotDiploidMultipleTargetState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator, hotspotMutator);

        state.record(statistics);

        Frame<SimpleHotspotDiploid> frame = new SimpleHotspotDiploidMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);

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
