package ga.frame.frames;

import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.frame.states.State;
import ga.frame.states.DiploidState;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class SimpleDiploidFrame<C extends Chromosome> extends Frame<C> {

    public SimpleDiploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics,
                              @NotNull final DynamicHandler<C> handler) {
        super(state, postOperator, statistics, handler);
    }

    public SimpleDiploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics) {
        super(state, postOperator, statistics);
    }

    public SimpleDiploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics,
                              @NotNull final PriorOperator<C> priorOperator) {
        super(state, postOperator, statistics, priorOperator);
    }

    public SimpleDiploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics,
                              @NotNull final PriorOperator<C> priorOperator,
                              @NotNull final DynamicHandler<C> handler) {
        super(state, postOperator, statistics, priorOperator, handler);
    }

    @Override
    public void evolve() {
        if (handler != null && handler.handle(state)) {
            statistics.nextGeneration();
            state.record(statistics);
            return;
        }
        if (priorOperator != null)
            state.preOperate(priorOperator);
        state.reproduce();
        state.mutate();
        ((DiploidState) state).mutateExpressionMap();
        state.postOperate(postOperator);
        state.nextGeneration();
        statistics.nextGeneration();
        state.evaluate(true);
        state.record(statistics);
    }
}
