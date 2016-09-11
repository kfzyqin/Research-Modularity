package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public abstract class GAState<T extends Chromosome> {

    protected int generation = 0;
    protected int numOfMates;
    protected final Population<T> population;
    protected Fitness fitness;
    protected Mutator mutator;
    protected Recombiner<T> recombiner;
    protected Selector<T> selector;

    public GAState(@NotNull final Population<T> population,
                   @NotNull final Fitness fitness,
                   @NotNull final Mutator mutator,
                   @NotNull final Recombiner<T> recombiner,
                   @NotNull final Selector<T> selector,
                   final int numOfMates) {
        this.population = population;
        this.fitness = fitness;
        this.mutator = mutator;
        this.recombiner = recombiner;
        this.selector = selector;
        this.numOfMates = numOfMates;
        evaluate(true);
    }

    public abstract void recombine();
    public abstract void mutate();

    public void evaluate(final boolean recomputePhenotype){
        population.evaluate(fitness, recomputePhenotype);
    }

    public void record(Statistics<T> statistics) {
        statistics.record(population.getIndividualsView());
    }

    public void preOperate(PriorOperator<T> priorOperator){
        population.setMode(PopulationMode.PRIOR);
        priorOperator.preOperate(population);
    }



    public void postOperate(PostOperator<T> postOperator) {
        population.setMode(PopulationMode.POST);
        postOperator.postOperate(population);}

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

    public Fitness getFitness() {
        return fitness;
    }

    public void setFitness(@NotNull final Fitness fitness) {
        this.fitness = fitness;
    }

    public void setMutator(@NotNull final Mutator mutator) {
        this.mutator = mutator;
    }

    public void setRecombiner(@NotNull final Recombiner<T> recombiner) {
        this.recombiner = recombiner;
    }
}