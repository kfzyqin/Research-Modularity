package ga.frame.frames;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.WithHotspot;
import ga.frame.states.DiploidMultipleTargetState;
import ga.frame.states.GenderHotspotMultipleTargetState;
import ga.frame.states.HotspotDiploidMultipleTargetState;
import ga.frame.states.State;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class SimpleHotspotDiploidFrame<C extends Chromosome & WithHotspot> extends SimpleDiploidFrame<C> {
    public SimpleHotspotDiploidFrame(@NotNull final State<C> state,
                                           @NotNull final PostOperator<C> postOperator,
                                           @NotNull final Statistics<C> statistics,
                                           @NotNull final DynamicHandler<C> handler) {
        super(state, postOperator, statistics, handler);
    }

    public SimpleHotspotDiploidFrame(@NotNull final State<C> state,
                                           @NotNull final PostOperator<C> postOperator,
                                           @NotNull final Statistics<C> statistics) {
        super(state, postOperator, statistics);
    }

    public SimpleHotspotDiploidFrame(@NotNull final State<C> state,
                                           @NotNull final PostOperator<C> postOperator,
                                           @NotNull final Statistics<C> statistics,
                                           @NotNull final PriorOperator<C> priorOperator) {
        super(state, postOperator, statistics, priorOperator);
    }

    public SimpleHotspotDiploidFrame(@NotNull final State<C> state,
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
        state.postOperate(postOperator);
        state.nextGeneration();
        state.evaluate(true);
        ((DiploidMultipleTargetState) state).mutateExpressionMap();
        ((HotspotDiploidMultipleTargetState) state).mutateHotspot();
        statistics.nextGeneration();
        state.record(statistics);
    }
}
