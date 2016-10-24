package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;

/**
 * Created by david on 2/09/16.
 */
public class SimpleFrame<C extends Chromosome> extends Frame<C> {

    public SimpleFrame(@NotNull final State<C> state,
                       @NotNull final PostOperator<C> postOperator,
                       @NotNull final Statistics<C> statistics,
                       @NotNull final DynamicHandler<C> handler) {
        super(state, postOperator, statistics, handler);
    }

    public SimpleFrame(@NotNull final State<C> state,
                       @NotNull final PostOperator<C> postOperator,
                       @NotNull final Statistics<C> statistics) {
        super(state, postOperator, statistics);
    }

    public SimpleFrame(@NotNull final State<C> state,
                       @NotNull final PostOperator<C> postOperator,
                       @NotNull final Statistics<C> statistics,
                       @NotNull final PriorOperator<C> priorOperator) {
        super(state, postOperator, statistics, priorOperator);
    }

    public SimpleFrame(@NotNull final State<C> state,
                       @NotNull final PostOperator<C> postOperator,
                       @NotNull final Statistics<C> statistics,
                       @NotNull final PriorOperator<C> priorOperator,
                       @NotNull final DynamicHandler<C> handler) {
        super(state, postOperator, statistics, priorOperator, handler);
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
        state.reproduce();
        state.mutate();
        state.postOperate(postOperator);
        state.nextGeneration();
        state.evaluate(true);
        statistics.nextGeneration();
        state.record(statistics);
    }
}
