package experiments.experiment2;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SingleObjectDiploidFrame;
import ga.frame.states.SimpleDiploidState;
import ga.frame.states.State;
import ga.operations.dominanceMapMutators.DiploidDominanceMapMutator;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunction;
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
import java.util.Date;

/**
 * Created by Zhenyue Qin on 6/06/2017.
 * The Australian National University.
 */
public class DiploidGRNMain {
    private static final int[] target = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.001;
    private static final int numElites = 20;

    private static final int size = 200;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.8;
//    private static final int maxGen = 1000;
    private static final int maxGen = 200;

    private static final double maxFit = 300;
    private static final double epsilon = .5;

    private static final String summaryFileName = "Diploid-GRN.sum";
    private static final String csvFileName = "Diploid-GRN.csv";
    private static final String outputDirectory = "diploid-grn";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();
    private static final String plotTitle = "Diploid GRN Summary";
    private static final String plotFileName = "Diploid-GRN-Chart.png";

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle, perturbations);

        // Initializer
        DiploidGRNInitializer initializer = new DiploidGRNInitializer(size, target, edgeSize, dominanceMutationRate);

        // Population
        Population<SimpleDiploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize);

        PriorOperator<SimpleDiploid> priorOperator = new SimpleElitismOperator<>(numElites);

        Reproducer<SimpleDiploid> reproducer = new SimpleDiploidReproducer();

        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize));

        DetailedStatistics<SimpleDiploid> statistics = new DetailedStatistics<>();

        ExpressionMapMutator expressionMapMutator = new DiploidDominanceMapMutator(dominanceMutationRate);

        State<SimpleDiploid> state = new SimpleDiploidState<>(population, fitnessFunction, mutator, reproducer,
                selector, 2, reproductionRate, expressionMapMutator);

        state.record(statistics);
        Frame<SimpleDiploid> frame = new SingleObjectDiploidFrame<>(state, fillingOperator, statistics, priorOperator);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
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
