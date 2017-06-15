package examples.experiment4;

import ga.collections.Population;
import ga.components.chromosomes.GenderDiploid;
import ga.components.chromosomes.GenderHotspotDiploid;
import ga.components.chromosomes.SimpleDiploid;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunction;
import ga.operations.initializers.DiploidGRNInitializer;
import ga.operations.initializers.GenderDiploidGRNInitializer;
import ga.operations.initializers.GenderHotspotDiploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.priorOperators.SimpleGenderElitismOperator;
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
 * Created by zhenyueqin on 15/6/17.
 */
public class GenderHotspotDiploidGRNMain {
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

    private static final String summaryFileName = "Gender-Hotspot-Diploid-GRN.sum";
    private static final String csvFileName = "Gender-Hotspot-Diploid-GRN.csv";
    private static final String outputDirectory = "gender-hotspot-diploid-grn";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    public static void main(String[] args) throws IOException {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle, perturbations);

        // Initializer
        GenderHotspotDiploidGRNInitializer initializer = new GenderHotspotDiploidGRNInitializer(
                size, target, edgeSize, dominanceMutationRate, hotspotSize, hotspotMutationRate);

        // Population
        Population<GenderDiploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(geneMutationRate);

        // Selector for reproduction
        Selector<GenderDiploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);

        PriorOperator<GenderDiploid> priorOperator = new SimpleGenderElitismOperator<>(numElites);

        PostOperator<GenderDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));


    }
}
