package examples.experiment5;

import ga.collections.DetailedStatistics;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.Frame;
import ga.frame.SimpleFrame;
import ga.frame.SimpleState;
import ga.frame.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunction;
import ga.operations.initializers.HaploidGRNInitializer;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleHaploidReproducer;
import ga.operations.selectionOperators.selectionSchemes.ProportionalScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class HaploidGRNMain {
    private static final int[] target = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 500;
    private static final double mutationRate = 0.004;

    private static final int size = 200;
    private static final int tournamentSize = 5;
    private static final double selectivePressure = 1.0;

    private static final int maxGen = 2000;
    private static final double crossoverRate = 1.0;
    private static final double epsilon = .5;
    private static final double maxFit = 501;

    private static final String summaryFileName = "Haploid-GRN.sum";
    private static final String csvFileName = "Haploid-GRN.csv";
    private static final String outputDirectory = "haploid-grn";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle, perturbations);

        // It is not necessary to write an initializer, but doing so is convenient to repeat the experiment
        // using different parameter.
        Initializer<SimpleHaploid> initializer = new HaploidGRNInitializer(size, target, edgeSize);

        // Population
        Population<SimpleHaploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(mutationRate);

        // Selector for reproduction
        // Selector<SimpleHaploid> selector = new SimpleProportionalSelector<>();
        Selector<SimpleHaploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);

        // PostOperator is required to fill up the vacancy.
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(new ProportionalScheme());

        // Statistics for keeping track the performance in generations
        DetailedStatistics<SimpleHaploid> statistics = new DetailedStatistics();
        // Reproducer for reproduction
        Reproducer<SimpleHaploid> reproducer = new SimpleHaploidReproducer();

        State<SimpleHaploid> state = new SimpleState<>(population, fitnessFunction, mutator, reproducer, selector, 2, crossoverRate);
        state.record(statistics);
        Frame<SimpleHaploid> frame = new SimpleFrame<>(state,postOperator,statistics);

        statistics.print(0);
        statistics.setDirectory(outputDirectory + "/" + dateFormat.format(date));
        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon)
                break;
        }
        statistics.save(summaryFileName);
        statistics.generateCSVFile(csvFileName);
        statistics.generatePlot("Haploid GRN Summary", "Haploid-GRN-Chart.png");
    }
}
