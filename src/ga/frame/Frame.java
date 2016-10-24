package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosomes.Chromosome;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;

/**
 * This class provides a general framework for the genetic algorithm to run.
 * The abstract method 'evolve' is the content for one iteration of GA.
 *
 * @author Siu Kei Muk (David)
 * @since 29/08/16.
 */
public abstract class Frame<C extends Chromosome> {

    protected final State<C> state;
    protected Statistics<C> statistics;
    protected PriorOperator<C> priorOperator = null;
    protected PostOperator<C> postOperator;
    protected DynamicHandler<C> handler = null;

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
