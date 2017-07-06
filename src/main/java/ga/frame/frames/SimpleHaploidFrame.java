package ga.frame.frames;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.frame.states.State;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Created by david and Zhenyue Qin on 2/09/16.
 */
public class SimpleHaploidFrame<C extends Chromosome> extends Frame<C> {

    public SimpleHaploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics,
                              @NotNull final DynamicHandler<C> handler) {
        super(state, postOperator, statistics, handler);
    }

    public SimpleHaploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics) {
        super(state, postOperator, statistics);
    }

    public SimpleHaploidFrame(@NotNull final State<C> state,
                              @NotNull final PostOperator<C> postOperator,
                              @NotNull final Statistics<C> statistics,
                              @NotNull final PriorOperator<C> priorOperator) {
        super(state, postOperator, statistics, priorOperator);
    }

    public SimpleHaploidFrame(@NotNull final State<C> state,
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
        statistics.nextGeneration();
        state.evaluate(true);
        state.record(statistics);
    }
}
