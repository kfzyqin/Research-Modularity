package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.frame.GAFrame;
import ga.operations.postOperators.PostOperator;

/**
 * Created by david on 9/10/16.
 */
public class SimpleGenderGAFrame<G extends Chromosome & Coupleable<H>, H> extends GAFrame<G> {

    public SimpleGenderGAFrame(@NotNull GenderGAState<G, H> state,
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
        state.recombine();
        state.mutate();
        state.postOperate(postOperator);
        state.nextGeneration();
        state.evaluate(true);
        ((GenderGAState<G, H>) state).mutateHotspots();
        statistics.nextGeneration();
        state.record(statistics);
    }
}
