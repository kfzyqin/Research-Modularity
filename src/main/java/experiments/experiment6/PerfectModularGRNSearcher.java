package experiments.experiment6;

import ga.collections.DetailedStatistics;
import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.frames.Frame;
import ga.frame.frames.SimpleHaploidFrame;
import ga.frame.states.SimpleHaploidState;
import ga.frame.states.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.initializers.HaploidGRNInitializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.GRNFixedEdgeNoModularMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.GRNHaploidHorizontalOneGeneReproducer;
import ga.operations.reproducers.GRNHaploidNoXReproducer;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleProportionalSelector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This file belongs to the experiments to compare crossover at GRN diagonals crossover
 * specified at Larson's paper.
 *
 * Created by Zhenyue Qin on 23/06/2017.
 * The Australian National University.
 */
public class PerfectModularGRNSearcher {
    /* The two targets that the GA evolve towards */
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    /* Parameters of the GRN */
    private static final int maxCycle = 30;
    private static final int edgeSize = 22;
    private static final int perturbations = 500;
    private static final double perturbationRate = 0.15;

    /* Parameters of the GA */
    private static final double geneMutationRate = 0.15;
    private static final int numElites = 10;
    private static final int populationSize = 100;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.9;

    private static final int maxGen = 2000;
    private static final List<Integer> thresholds = Arrays.asList(0, 1); // when to switch targets
    private static final double alpha = 0.75;
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int perturbationCycleSize = perturbations;

    /* Settings for text outputs */
    private static final String summaryFileName = "Summary.txt";
    private static final String csvFileName = "Statistics.csv";
    private static final String outputDirectory = "fixed-record-esw-balanced-combinations-elite-p00";
    private static final String mainFileName = "HaploidGRNMatrixMain.java";
    private static final String allPerturbationsName = "Perturbations.per";
    private static final String modFitNamePrefix = "phenotypes";
    private static final String allPopulationPhenotypeName = "./population-phenotypes/all-population-phenotype";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    /* Settings for graph outputs */
    private static final String plotTitle = "Haploid GRN Matrix";
    private static final String plotFileName = "Trends.png";

    private static final double stride = 0.00;
    private static final int PerturbationPathUpBound = 4;

    public static void main(String[] args) throws IOException, InterruptedException {
//        int[][] targets = {target1, target2, target3, target4, target5, target6, target7};
        int[][] targets = {target1, target2};

        /* Fitness function */
//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargets(
//                targets, maxCycle, perturbations, perturbationRate, thresholds);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
//                targets, maxCycle, perturbations, perturbationRate, thresholds, stride);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetricResample(
//                targets, maxCycle, perturbations, perturbationRate, thresholds, stride, PerturbationPathUpBound);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsFast(
//                targets, maxCycle, perturbations, perturbationRate, thresholds, perturbationCycleSize);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinations(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationsAsymmetric(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetric(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        /* It is not necessary to write an initializer, but doing so is convenient to
        repeat the experiment using different parameter */
        HaploidGRNInitializer initializer = new HaploidGRNInitializer(populationSize, target1.length, edgeSize);

        /* Population */
        Population<SimpleHaploid> population = initializer.initializeModularizedPopulation(5);

//        Population<SimpleHaploid> population = initializer.initialize();
//        System.out.println(population.getIndividualsView().get(0));
//        int count = 0;
//        for (int i=0; i<population.getIndividualsView().get(0).getChromosome().getLength(); i++) {
//            System.out.print(population.getIndividualsView().get(0).getChromosome().getPhenotype(false).getGene(i));
//            System.out.print("\t");
//            count += 1;
//            if (count % 10 == 0) {
//                System.out.println();
//            }
//        }


        /* Mutator for chromosomes */
        Mutator mutator = new GRNFixedEdgeNoModularMutator(geneMutationRate);

        /* Selector for reproduction */
//        Selector<SimpleHaploid> selector = new SimpleTournamentSelector<>(tournamentSize);
        Selector<SimpleHaploid> selector = new SimpleProportionalSelector<>();

        /* Selector for elites */
        PriorOperator<SimpleHaploid> priorOperator = new SimpleElitismOperator<>(numElites);

        /* PostOperator is required to fill up the vacancy */
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(
                new SimpleTournamentScheme(3));

        /* Reproducer for reproduction */
        Reproducer<SimpleHaploid> reproducer = new GRNHaploidNoXReproducer();
//        Reproducer<SimpleHaploid> reproducer = new GRNHaploidMatrixDiagonalReproducer(target1.length);
//        Reproducer<SimpleHaploid> reproducer = new GRNHaploidHorizontalOneGeneReproducer(target1.length);

        /* Statistics for keeping track the performance in generations */
        DetailedStatistics<SimpleHaploid> statistics = new DetailedStatistics<>();

        /* Set output paths */
        String outputDirectoryPath = outputDirectory + "/" + dateFormat.format(date);
        statistics.setDirectory(outputDirectoryPath);
        statistics.copyMainFile(mainFileName, System.getProperty("user.dir") +
                "/src/main/java/experiments/exp6_haploid_hotspot_aid_matrix_x_validation/" + mainFileName);

        /* The state of an GA */
        State<SimpleHaploid> state = new SimpleHaploidState<>(
                population, fitnessFunction, mutator, reproducer, selector, 2, reproductionRate);
        state.record(statistics); // record the initial state of an population

        /* The frame of an GA to change states */
        Frame<SimpleHaploid> frame = new SimpleHaploidFrame<>(state,postOperator,statistics, priorOperator);

        statistics.print(0); // print the initial state of an population
        /* Actual GA evolutions */
        Set<String> aIndSet = new HashSet<>();
        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            for (Individual e : population.getIndividualsView()) {
                if (e.getFitness() > 0.9462) {
                    String strInd = e.getChromosome().getPhenotype(false).toString();
                    if (!aIndSet.contains(strInd)) {
                        aIndSet.add(strInd);
                        System.out.println("A new good individual: ");
                        System.out.println(strInd);
                    }
                }
            }
            System.out.println("here is a complete set printing");
            for (String e : aIndSet) {
                System.out.println(e);
            }
            System.out.println();
        }

        /* Generate output files */
        statistics.save(summaryFileName);
        statistics.generateFitnessModularityGRNs(modFitNamePrefix);
        statistics.generatePopulationPhenotypesOfAllGenerations(allPopulationPhenotypeName);
        statistics.generateNormalCSVFile(csvFileName);
        statistics.generatePlot(plotTitle, plotFileName);

//        String test = Arrays.deepToString(targets);
//
//        ProcessBuilder PB1 = new ProcessBuilder("python2", "./python-tools/java_plot_curves.py",
//                System.getProperty("user.dir") + "/generated-outputs/" + outputDirectoryPath,
//                "" + perturbations, test, thresholds.toString());
//        PB1.start();

//        statistics.storePerturbations(allPerturbationsName);
    }
}
