package ga.frame.frames;

import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.frame.states.State;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;
import org.jetbrains.annotations.NotNull;

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
 * This class provides a general framework for the genetic algorithm to run.
 * The abstract method 'evolve' is the content for one iteration of GA.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 29/08/16.
 */
public abstract class Frame<C extends Chromosome> {

    protected final State<C> state;
    protected Statistics<C> statistics;
    protected PriorOperator<C> priorOperator = null;
    protected PostOperator<C> postOperator;
    protected DynamicHandler<C> handler = null;

    /**
     * Constructs a frame for GA
     * @param state State of genetic algorithm
     * @param postOperator
     * @param statistics
     */
    public Frame(@NotNull final State<C> state,
                 @NotNull final PostOperator<C> postOperator,
                 @NotNull final Statistics<C> statistics) {
        this.state = state;
        this.postOperator = postOperator;
        this.statistics = statistics;
    }

    public Frame(@NotNull final State<C> state,
                 @NotNull final PostOperator<C> postOperator,
                 @NotNull final Statistics<C> statistics,
                 @NotNull final DynamicHandler<C> handler) {
        this(state, postOperator, statistics);
        this.handler = handler;
    }

    public Frame(@NotNull final State<C> state,
                 @NotNull final PostOperator<C> postOperator,
                 @NotNull final Statistics<C> statistics,
                 @NotNull final PriorOperator<C> priorOperator) {
        this(state, postOperator, statistics);
        this.priorOperator = priorOperator;
    }

    public Frame(@NotNull final State<C> state,
                 @NotNull final PostOperator<C> postOperator,
                 @NotNull final Statistics<C> statistics,
                 @NotNull final PriorOperator<C> priorOperator,
                 @NotNull final DynamicHandler<C> handler) {
        this(state, postOperator, statistics, priorOperator);
        this.handler = handler;
    }

    /**
     * Evolves for one generation
     */
    public abstract void evolve();

    public State<C> getState() {
        return state;
    }

    public Statistics<C> getStatistics() {
        return statistics;
    }

    public void setStatistics(final Statistics<C> statistics) {
        this.statistics = statistics;
    }

    public void setPriorOperator(final PriorOperator<C> priorOperator){
        this.priorOperator = priorOperator;
    }

    public void setPostOperator(@NotNull final PostOperator<C> postOperator) {
        this.postOperator = postOperator;
    }

    public void setDynamicHandler(final DynamicHandler<C> handler) {
        this.handler = handler;
    }
}
