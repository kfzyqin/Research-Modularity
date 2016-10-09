package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.dynamicHandler.DynamicHandler;
import ga.operations.postOperators.PostOperator;

/**
 * Created by david on 2/09/16.
 */
public class SimpleGAFrame<C extends Chromosome> extends GAFrame<C> {

    public SimpleGAFrame(@NotNull final GAState<C> state,
                         @NotNull final PostOperator<C> postOperator,
                         @NotNull final Statistics<C> statistics,
                         final DynamicHandler<C> handler) {
        super(state, postOperator, statistics);
        this.handler = handler;
    }

    public SimpleGAFrame(@NotNull final GAState<C> state,
                         @NotNull final PostOperator<C> postOperator,
                         @NotNull final Statistics<C> statistics) {
        super(state, postOperator, statistics);
    }

    public void setHandler(final DynamicHandler<C> handler) {
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
