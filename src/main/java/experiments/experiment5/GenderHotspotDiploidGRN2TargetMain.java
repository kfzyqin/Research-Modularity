package experiments.experiment5;

import ga.collections.DetailedGenderStatistics;
import ga.collections.Population;
import ga.components.chromosomes.GenderHotspotDiploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleGenderHotspotMultipleTargetFrame;
import ga.frame.states.SimpleGenderHotspotMultipleTargetState;
import ga.frame.states.State;
import ga.operations.dominanceMapMutators.DiploidDominanceMapMutator;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.hotspotMutators.RandomHotspotMutator;
import ga.operations.initializers.GenderHotspotDiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleGenderElitismOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleGenderHotspotReproducer;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleTournamentCoupleSelector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zhenyueqin on 20/6/17.
 */
public class GenderHotspotDiploidGRN2TargetMain {
    private static final int[] target1 = {1, -1, 1, -1, 1, -1};
    private static final int[] target2 = {1, -1, 1, 1, -1, 1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final int hotspotSize = 9;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.0005;
    private static final double hotspotMutationRate = 0.00025;
    private static final double perturbationRate = 0.15;
    private static final int numElites = 20;

    private static final int size = 200;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.95;
    private static final int maxGen = 2000;

    private static final double maxFit = 300;
    private static final double epsilon = 0;

    private static final int numberOfChildren = 2;

    private static final String summaryFileName = "Gender-Hotspot-Diploid-GRN-2-Target.sum";
    private static final String csvFileName = "Gender-Hotspot-Diploid-GRN-2-Target.csv";
    private static final String outputDirectory = "gender-hotpost-diploid-grn-2-target";
    private static final String mainFileName = "GenderHotspotDiploidGRN2TargetMain.java";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Gender Hotspot GRN 2 Targets Summary";
    private static final String plotFileName = "Gender-Hotspot-GRN-2-Target-Chart.png";

    private static final List<Integer> thresholds = Arrays.asList(0, 500);

    public static void main(String[] args) throws IOException {

        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(target1, target2, maxCycle,
                perturbations, perturbationRate, thresholds);

        GenderHotspotDiploidGRNInitializer initializer =
                new GenderHotspotDiploidGRNInitializer(size, target1.length, edgeSize, hotspotSize);

        Population<GenderHotspotDiploid> population = initializer.initialize();

        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        Selector<GenderHotspotDiploid> selector = new SimpleTournamentCoupleSelector<>(tournamentSize);

        PriorOperator<GenderHotspotDiploid> priorOperator = new SimpleGenderElitismOperator<>(numElites);

        PostOperator<GenderHotspotDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        Reproducer<GenderHotspotDiploid> reproducer = new SimpleGenderHotspotReproducer(numberOfChildren);

        DetailedGenderStatistics<GenderHotspotDiploid> statistics = new DetailedGenderStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        HotspotMutator hotspotMutator = new RandomHotspotMutator(hotspotMutationRate);

        State<GenderHotspotDiploid> state = new SimpleGenderHotspotMultipleTargetState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator, hotspotMutator);

        state.record(statistics);

        Frame<GenderHotspotDiploid> frame = new SimpleGenderHotspotMultipleTargetFrame<>(state, fillingOperator, statistics, priorOperator);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/experiment5/" + mainFileName);

        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if ((statistics.getOptimum(i) > maxFit - epsilon) && (statistics.getGeneration() > thresholds.get(thresholds.size()-1))) {
                break;
            }
        }
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);
    }
}
