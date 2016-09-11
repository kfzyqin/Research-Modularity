package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.dynamicHandler.DynamicHandler;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

/**
 * Created by david on 29/08/16.
 */
public abstract class GAFrame<T extends Chromosome> {

    protected final GAState<T> state;
    protected Statistics<T> statistics;
    protected PriorOperator<T> priorOperator = null;
    protected PostOperator<T> postOperator;
    protected DynamicHandler<T> handler = null;

    public GAFrame(@NotNull final GAState<T> state,
                   @NotNull final PostOperator<T> postOperator,
                   @NotNull final Statistics<T> statistics) {
        this.state = state;
        this.postOperator = postOperator;
        this.statistics = statistics;
    }

    /*
    public GAFrame(@NotNull final Fitness fitness,
                   @NotNull final Initializer<T> initializer,
                   @NotNull final Recombiner<T> recombiner,
                   @NotNull final Mutator mutator,
                   @NotNull final Selector<T> selector,
                   @NotNull final Statistics<T> statistics,
                   final int numOfMates) {
        Population<T> population = initializer.initialize();
        state = new GAState<>(population, fitness, mutator, recombiner, selector, numOfMates);
        this.statistics = statistics;
        state.record(statistics);
    }*/

    public abstract void evolve();

    public GAState<T> getState() {
        return state;
    }

    public Statistics<T> getStatistics() {
        return statistics;
    }

    public void setStatistics(final Statistics<T> statistics) {
        this.statistics = statistics;
    }

    public void setPriorOperator(final PriorOperator<T> priorOperator){
        this.priorOperator = priorOperator;
    }

    public void setPostOperator(@NotNull final PostOperator<T> postOperator) {
        this.postOperator = postOperator;
    }

    public void setDynamicHandler(final DynamicHandler<T> handler) {
        this.handler = handler;
    }
}
