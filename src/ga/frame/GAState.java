package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.mutation.MutationOperator;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.recombination.Recombiner;
import ga.operations.selectors.Selector;

/**
 * This class provides an overall base implementation for the state of current generation of genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public abstract class GAState<T extends Chromosome> {

    protected int generation = 0;
    protected int numOfMates;
    protected final Population<T> population;
    protected FitnessFunction fitnessFunction;
    protected MutationOperator mutationOperator;
    protected Recombiner<T> recombiner;
    protected Selector<T> selector;

    /**
     * Constructs an initial state for the GA
     * @param population initial population
     * @param fitnessFunction fitnessfunction function
     * @param mutationOperator mutation operator
     * @param recombiner recombination operator
     * @param selector parents selector
     * @param numOfMates number of parents per recombination
     */
    public GAState(@NotNull final Population<T> population,
                   @NotNull final FitnessFunction fitnessFunction,
                   @NotNull final MutationOperator mutationOperator,
                   @NotNull final Recombiner<T> recombiner,
                   @NotNull final Selector<T> selector,
                   final int numOfMates) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.mutationOperator = mutationOperator;
        this.recombiner = recombiner;
        this.selector = selector;
        this.numOfMates = numOfMates;
        evaluate(true);
    }

    /**
     * Performs recombination using the recombination operator.
     */
    public abstract void recombine();

    /**
     * Performs mutation using the mutation operator.
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
    public void record(Statistics<T> statistics) {
        statistics.record(population.getIndividualsView());
    }

    /**
     * Performs operation before reproduction
     * @param priorOperator
     */
    public void preOperate(PriorOperator<T> priorOperator){
        population.setMode(PopulationMode.PRIOR);
        priorOperator.preOperate(population);
    }

    /**
     * Performs operation after reproduction
     * @param postOperator
     */
    public void postOperate(PostOperator<T> postOperator) {
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

    public Population<T> getPopulation(){
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

    public void setMutationOperator(@NotNull final MutationOperator mutationOperator) {
        this.mutationOperator = mutationOperator;
    }

    public void setRecombiner(@NotNull final Recombiner<T> recombiner) {
        this.recombiner = recombiner;
    }
}