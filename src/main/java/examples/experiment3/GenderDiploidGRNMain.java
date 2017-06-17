package examples.experiment3;

import ga.collections.DetailedGenderStatistics;
import ga.collections.Population;
import ga.components.chromosomes.GenderDiploid;
import ga.components.chromosomes.SimpleDiploid;
import ga.frame.Frame;
import ga.frame.SimpleFrame;
import ga.frame.SimpleState;
import ga.frame.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunction;
import ga.operations.initializers.GenderDiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleGenderElitismOperator;
import ga.operations.reproducers.GenderDiploidReproducer;
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
 * Created by zhenyueqin on 17/6/17.
 */
public class GenderDiploidGRNMain {
    private static final int[] target = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 500;
    private static final int hotspotSize = 9;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.001;
    private static final double hotspotMutationRate = 0.0005;
    private static final int numElites = 10;

    private static final int size = 200;
    private static final int tournamentSize = 3;
    private static final double selectivePressure = 1.0;
    private static final double reproductionRate = 0.8;
    private static final int maxGen = 200;

    private static final double maxFit = 501;
    private static final double epsilon = .5;

    private static final int numberOfChildren = 2;

    private static final String summaryFileName = "Gender-Diploid-GRN.sum";
    private static final String csvFileName = "Gender-Diploid-GRN.csv";
    private static final String outputDirectory = "gender-diploid-grn";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Gender GRN Summary";
    private static final String plotFileName = "Gender-GRN-Chart.png";

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle, perturbations);

        // Initializer
        GenderDiploidGRNInitializer initializer =
                new GenderDiploidGRNInitializer(size, target, edgeSize, dominanceMutationRate);

        // Population
        Population<GenderDiploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<GenderDiploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);

        PriorOperator<GenderDiploid> priorOperator = new SimpleGenderElitismOperator<>(numElites);

        PostOperator<GenderDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));

        Reproducer<GenderDiploid> reproducer = new GenderDiploidReproducer(numberOfChildren, dominanceMutationRate);

        DetailedGenderStatistics<GenderDiploid> statistics = new DetailedGenderStatistics<>();

        State<GenderDiploid> state = new SimpleState<>(population, fitnessFunction, mutator, reproducer, selector, 2, reproductionRate);

        state.record(statistics);

        Frame<GenderDiploid> frame = new SimpleFrame<>(state, fillingOperator, statistics, priorOperator);

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


    }
}
