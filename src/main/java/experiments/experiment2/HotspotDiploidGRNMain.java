package experiments.experiment2;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHotspotDiploidFrame;
import ga.frame.states.SimpleHotspotDiploidMultipleTargetState;
import ga.frame.states.State;
import ga.operations.dominanceMapMutators.DiploidDominanceMapMutator;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionSingleTarget;
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
import ga.operations.reproducers.SimpleHotspotDiploidReproducer;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class HotspotDiploidGRNMain {
    private static final int[] target = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final int hotspotSize = 9;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.001;
    private static final double hotspotMutationRate = 0.0005;
    private static final double perturbationRate = 0.15;
    private static final int numElites = 20;

    private static final int numOfChildren = 2;

    private static final int size = 200;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.8;
    private static final int maxGen = 200;

    private static final double maxFit = 300;
    private static final double epsilon = .5;

    private static final String summaryFileName = "Hotspot-Diploid-GRN.sum";
    private static final String csvFileName = "Hotspot-Diploid-GRN.csv";
    private static final String outputDirectory = "hotspot-diploid-hotspot-grn";
    private static final String mainFileName = "HotspotDiploidGRNMain.java";

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();
    private static final String plotTitle = "Hotspot Diploid GRN Summary";
    private static final String plotFileName = "Hotspot-Diploid-GRN-Chart.png";

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunctionSingleTarget(target, maxCycle, perturbations, perturbationRate);

        // Initializer
        HotspotDiploidGRNInitializer initializer = new HotspotDiploidGRNInitializer(size, target.length, edgeSize, hotspotSize);

        // Population
        Population<SimpleHotspotDiploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<SimpleHotspotDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleHotspotDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        Reproducer<SimpleHotspotDiploid> reproducer = new SimpleHotspotDiploidReproducer();

        PostOperator<SimpleHotspotDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        DetailedStatistics<SimpleHotspotDiploid> statistics = new DetailedStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        HotspotMutator hotspotMutator = new RandomHotspotMutator(hotspotMutationRate);

        State<SimpleHotspotDiploid> state = new SimpleHotspotDiploidMultipleTargetState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator, hotspotMutator);

        state.record(statistics);
        Frame<SimpleHotspotDiploid> frame = new SimpleHotspotDiploidFrame<>(state, fillingOperator, statistics, priorOperator);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/experiment2/" + mainFileName);

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
