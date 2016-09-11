package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.dynamicHandler.DynamicHandler;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

/**
 * Created by david on 2/09/16.
 */
public class SimpleGAFrame<T extends Chromosome> extends GAFrame<T> {

    protected DynamicHandler<T> handler = null;

    public SimpleGAFrame(@NotNull final GAState<T> state,
                         @NotNull final PostOperator<T> postOperator,
                         @NotNull final Statistics<T> statistics,
                         final DynamicHandler<T> handler) {
        super(state, postOperator, statistics);
        this.handler = handler;
    }

    public SimpleGAFrame(@NotNull final GAState<T> state,
                         @NotNull final PostOperator<T> postOperator,
                         @NotNull final Statistics<T> statistics) {
        super(state, postOperator, statistics);
    }

    /*

    public SimpleGAFrame(@NotNull final Fitness fitness,
                         @NotNull final Initializer<T> initializer,
                         @NotNull final Recombiner<T> recombiner,
                         @NotNull final Mutator mutator,
                         @NotNull final Selector selector,
                         @NotNull final Statistics<T> statistics,
                         final int numOfMates) {
        super(new SimpleGAState<T>(fitness, initializer, recombiner, mutator, selector, statistics, numOfMates));
    }*/

    /*

    public SimpleGAFrame(@NotNull final Fitness fitness,
                         @NotNull final Initializer<T> initializer,
                         @NotNull final Recombiner<T> recombiner,
                         @NotNull final Mutator mutator,
                         @NotNull final Selector selector,
                         @NotNull final Statistics<T> statistics,
                         @NotNull final DynamicHandler<T> handler,
                         final int numOfMates) {
        super(fitness, initializer, recombiner, mutator, selector, statistics, numOfMates);
        this.handler = handler;
    }*/

    public void setHandler(final DynamicHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public void evolve(){
        if (handler != null && handler.handle(state)) {
            statistics.nextGeneration();
            state.record(statistics);
            return;
        }
        if (priorOperator != null)
            state.preOperate(priorOperator);
        state.recombine();
        state.mutate();
        state.postOperate(postOperator);
        state.nextGeneration();
        state.evaluate(true);
        statistics.nextGeneration();
        state.record(statistics);
    }
}
