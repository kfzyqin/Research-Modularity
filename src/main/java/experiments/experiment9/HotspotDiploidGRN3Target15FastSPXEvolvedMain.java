package experiments.experiment9;

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
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFastHidden;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.hotspotMutators.RandomHotspotMutator;
import ga.operations.initializers.HotspotDiploidGRNHiddenTargetInitializer;
import ga.operations.initializers.HotspotDiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleHotspotDiploidEvolvedSPXMatrixReproducer;
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
 * Created by Zhenyue Qin (秦震岳) on 25/6/17.
 * The Australian National University.
 */
public class HotspotDiploidGRN3Target15FastSPXEvolvedMain {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1,
            1, -1, 1, -1, 1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target3 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 20;
    private static final int edgeSize = 45;
    private static final int perturbations = 300;

    private static final double geneMutationRate = 0.05;
    private static final double dominanceMutationRate = 0.002;
    private static final double hotspotMutationRate = 0.05;
    private static final double perturbationRate = 0.15;
    private static final int numElites = 10;

    private static final int perturbationCycleSize = 100;

    private static final int size = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.9;
    private static final int maxGen = 3000;

    private static final double maxFit = 2;
    private static final double epsilon = 0.151;

    private static final String summaryFileName = "Hotspot-Diploid-GRN-3-Target-15-Evolved-SPX.txt";
    private static final String csvFileName = "Hotspot-Diploid-GRN-3-Target-15-Evolved-SPX.csv";
    private static final String outputDirectory = "hotspot-diploid-grn-3-target-15-evolved-spx";
    private static final String mainFileName = "HotspotDiploidGRN3Target15FastSPXEvolvedMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Hotspot Diploid GRN 3 Targets 15 Evolved SPX";
    private static final String plotFileName = "Hotspot Diploid-GRN-3-Target-15-Evolved-SPX.png";

    private static final List<Integer> thresholds = Arrays.asList(0, 500, 1300);

    public static void main(String[] args) throws IOException {
        int[][] targets = {target1, target2, target3};

        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFastHidden(targets,
                maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize, 3);

        // Initializer
        HotspotDiploidGRNHiddenTargetInitializer initializer =
                new HotspotDiploidGRNHiddenTargetInitializer(size, target1.length,3, edgeSize, target1.length);

        // Population
        Population<SimpleHotspotDiploid> population = initializer.initializeWithMatrixHotspot();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<SimpleHotspotDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleHotspotDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        PostOperator<SimpleHotspotDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        Reproducer<SimpleHotspotDiploid> reproducer = new SimpleHotspotDiploidEvolvedSPXMatrixReproducer(0.5, target1.length);

        DetailedStatistics<SimpleHotspotDiploid> statistics = new DetailedStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        HotspotMutator hotspotMutator = new RandomHotspotMutator(hotspotMutationRate);

        State<SimpleHotspotDiploid> state = new SimpleHotspotDiploidMultipleTargetState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator, hotspotMutator);

        state.record(statistics);

        Frame<SimpleHotspotDiploid> frame = new SimpleHotspotDiploidMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);
//        Frame<SimpleHotspotDiploid> frame = new SimpleHotspotDiploidMultipleTargetFrame<>(state, fillingOperator, statistics);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/experiment9/" + mainFileName);
        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if ((statistics.getOptimum(i) > maxFit - epsilon) &&
                    (statistics.getGeneration() > thresholds.get(thresholds.size()-1))) {
                break;
            }
        }
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);

    }
}
