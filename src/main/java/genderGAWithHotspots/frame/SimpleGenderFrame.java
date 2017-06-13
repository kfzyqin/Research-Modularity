package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.frame.Frame;
import ga.operations.postOperators.PostOperator;
import genderGAWithHotspots.components.chromosomes.Coupleable;

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
 * This frame is almost identical to SimpleFrame except that it takes care of the
 * hotspot mutation process.
 *
 * @author Siu Kei Muk (David)
 * @since 9/10/16.
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
//        ((GenderState<G, H>) state).mutateHotspots();
        statistics.nextGeneration();
        state.record(statistics);
    }
}
