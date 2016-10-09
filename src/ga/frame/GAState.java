package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.mutator.ChromosomeMutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.recombinator.Recombinator;
import ga.operations.selectors.Selector;

/**
 * This class provides an overall base implementation for the state of current generation of genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public abstract class GAState<C extends Chromosome> {

    protected int generation = 0;
    protected int numOfMates;
    protected final Population<C> population;
    protected FitnessFunction fitnessFunction;
    protected ChromosomeMutator chromosomeMutator;
    protected Recombinator<C> recombinator;
    protected Selector<C> selector;

    /**
     * Constructs an initial state for the GA
     * @param population initial population
     * @param fitnessFunction fitness function
     * @param chromosomeMutator mutator operator
     * @param recombinator recombinator operator
     * @param selector parents selector
     * @param numOfMates number of parents per reproduction
     */
    public GAState(@NotNull final Population<C> population,
                   @NotNull final FitnessFunction fitnessFunction,
                   @NotNull final ChromosomeMutator chromosomeMutator,
                   @NotNull final Recombinator<C> recombinator,
                   @NotNull final Selector<C> selector,
                   final int numOfMates) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.chromosomeMutator = chromosomeMutator;
        this.recombinator = recombinator;
        this.selector = selector;
        this.numOfMates = numOfMates;
        evaluate(true);
    }

    /**
     * Performs recombinator using the recombinator operator.
     */
    public abstract void recombine();

    /**
     * Performs mutator using the mutator operator.
     */
    public abstract void mutate();

    /**
     * Evaluates the fitnessfunction function value of the whole population
     * @param recomputePhenotype determines whether to force re-computation of phenotype from genotype
     */
    public void evaluate(final boolean recomputePhenotype){
        population.evaluate(fitnessFunction, recomputePhenotype);
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
        return population.nextGeneration();
    }

    public Population<C> getPopulation(){
        return population;
    }

    public int getGeneration() {
        return generation;
    }

    /**
     * @return the fitnessfunction function (not value) being used in the GA
     */
    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(@NotNull final FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setChromosomeMutator(@NotNull final ChromosomeMutator chromosomeMutator) {
        this.chromosomeMutator = chromosomeMutator;
    }

    public void setRecombinator(@NotNull final Recombinator<C> recombinator) {
        this.recombinator = recombinator;
    }
}