package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.frame.Frame;
import ga.operations.postOperators.PostOperator;

/**
 * Created by david on 9/10/16.
 */
public class SimpleGenderFrame<G extends Chromosome & Coupleable<H>, H> extends Frame<G> {

    public SimpleGenderFrame(@NotNull GenderState<G, H> state,
                             @NotNull PostOperator<G> postOperator,
                             @NotNull Statistics<G> statistics) {
        super(state, postOperator, statistics);
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
        ((GenderState<G, H>) state).mutateHotspots();
        statistics.nextGeneration();
        state.record(statistics);
    }
}
