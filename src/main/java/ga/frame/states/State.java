package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.hotspots.Hotspot;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.ExtendedTournamentSelector;
import ga.operations.selectionOperators.selectors.Selector;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides an overall base implementation for the state of current generation of genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public abstract class State<C extends Chromosome> {

    protected int generation = 0;
    protected int numOfMates;
    protected final Population<C> population;
    protected FitnessFunction fitnessFunction;
    protected Mutator mutator;
    protected Reproducer<C> reproducer;
    protected Selector<C> selector;
    protected double k;


    /**
     * Constructs an initial state for the GA
     * @param population initial population
     * @param fitnessFunction fitness function
     * @param mutator mutators operator
     * @param reproducer reproducers operator
     * @param selector parents selector
     * @param numOfMates number of parents per reproduction
     */
    public State(@NotNull final Population<C> population,
                 @NotNull final FitnessFunction fitnessFunction,
                 @NotNull final Mutator mutator,
                 @NotNull final Reproducer<C> reproducer,
                 @NotNull final Selector<C> selector,
                 final int numOfMates) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.mutator = mutator;
        this.reproducer = reproducer;
        this.selector = selector;
        this.numOfMates = numOfMates;
        evaluate(true);
    }

    public State(@NotNull final Population<C> population,
                 @NotNull final FitnessFunction fitnessFunction,
                 @NotNull final Mutator mutator,
                 @NotNull final Reproducer<C> reproducer,
                 @NotNull final Selector<C> selector,
                 final int numOfMates,
                 double k) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.mutator = mutator;
        this.reproducer = reproducer;
        this.selector = selector;
        this.numOfMates = numOfMates;
        this.k = k;
        evaluate(true);
    }

    public boolean addTarget() {
        List<Double> fitness = new ArrayList<>();


        for (Individual<C> individual: population.getIndividualsView()) {
            fitness.add(individual.getFitness());
        }
        double kproportion = GeneralMethods.kproportion(k, fitness);
//        GeneralMethods.best(fitness);

        double best = 0.950212931632136;

        if (kproportion == best) return true;
        else return false;

    }

    /**
     * Performs recombination using the reproducers operator.
     */
    public abstract void reproduce();

    /**
     * Performs mutators using the mutation operator.
     */
    public abstract void mutate();

    /**
     * Evaluates the fitnessFunctions function value of the whole population
     * @param recomputePhenotype determines whether to force re-computation of phenotype from genotype
     */
    public void evaluate(final boolean recomputePhenotype){
//        ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = ((ExtendedTournamentSelector<SimpleHaploid>) selector).threshold;
        if (fitnessFunction instanceof GRNFitnessFunctionMultipleTargets && !((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdIsAtMaxLength()) {
            List<Integer> threshold = new ArrayList<>();
            for (int i: ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget) {
                threshold.add(i);
            }
            if (addTarget()) {
                threshold.add(this.generation);
                ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
            } else if (this.generation == 500) {
                threshold.add(this.generation);
                ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
            }
        }

//        if(addTarget()){
//            if (fitnessFunction instanceof GRNFitnessFunctionMultipleTargets && !((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdIsAtMaxLength()) {
//                List<Integer> threshold = new ArrayList<>();
//                for (int i: ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget) {
//                    threshold.add(i);
//                }
//                threshold.add(this.generation);
//                ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
//            }
//        } else if (this.generation == 500 &&
//                fitnessFunction instanceof GRNFitnessFunctionMultipleTargets &&
//                !((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdIsAtMaxLength()) {
//            List<Integer> threshold = new ArrayList<>();
//            for (int i: ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget) {
//                threshold.add(i);
//            }
//            threshold.add(this.generation);
//            ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
//        }
        population.evaluate(fitnessFunction, recomputePhenotype);
    }

    public void evaluateWithMultipleTargets(final boolean recomputePhenotype) {
//        ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = ((ExtendedTournamentSelector<SimpleHaploid>) selector).threshold;
        if (fitnessFunction instanceof GRNFitnessFunctionMultipleTargets && !((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdIsAtMaxLength()) {
            List<Integer> threshold = new ArrayList<>();
            for (int i: ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget) {
                threshold.add(i);
            }
            if (addTarget()) {
                threshold.add(this.generation);
                ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
            } else if (this.generation == 500) {
                threshold.add(this.generation);
                ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
            }
        }

//        if(addTarget()){
//            if (fitnessFunction instanceof GRNFitnessFunctionMultipleTargets && !((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdIsAtMaxLength()) {
//                List<Integer> threshold = new ArrayList<>();
//                for (int i: ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget) {
//                    threshold.add(i);
//                }
//                threshold.add(this.generation);
//                ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
//            }
//        } else if (this.generation == 500 &&
//                fitnessFunction instanceof GRNFitnessFunctionMultipleTargets &&
//                !((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdIsAtMaxLength()) {
//            List<Integer> threshold = new ArrayList<>();
//            for (int i: ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget) {
//                threshold.add(i);
//            }
//            threshold.add(this.generation);
//            ((GRNFitnessFunctionMultipleTargets) fitnessFunction).thresholdOfAddingTarget = threshold;
//        }
        population.evaluate((FitnessFunctionMultipleTargets) fitnessFunction, recomputePhenotype, this.generation);
    }

    /**
     * Records the information of the current population to a given statistics bookkeeper
     * @param statistics
     */
    public void record(Statistics<C> statistics) {
        statistics.record(population.getIndividualsView());
    }

    /**
     * Performs operation before reproduction
     * @param priorOperator
     */
    public void preOperate(PriorOperator<C> priorOperator){
        population.setMode(PopulationMode.PRIOR);
        priorOperator.preOperate(population);
    }

    /**
     * Performs operation after reproduction
     * @param postOperator
     */
    public void postOperate(PostOperator<C> postOperator) {
        population.setMode(PopulationMode.POST);
        postOperator.postOperate(population);
    }

    /**
     * Notifies the state to proceed to the next generation
     * @return a boolean of whether the next generation is successfully reached
     */
    public boolean nextGeneration(){
        generation++;
        if (selector instanceof ExtendedTournamentSelector) {
            ((ExtendedTournamentSelector<C>) selector).setGeneration(generation);
        }
        return population.nextGeneration();
    }

    public Population<C> getPopulation(){
        return population;
    }

    public int getGeneration() {
        return generation;
    }

    /**
     * @return the fitness function (not value) being used in the GA
     */
    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(@NotNull final FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setMutator(@NotNull final Mutator mutator) {
        this.mutator = mutator;
    }

    public void setReproducer(@NotNull final Reproducer<C> reproducer) {
        this.reproducer = reproducer;
    }

    public void setSelector(Selector<C> selector) {
        this.selector = selector;
    }
}