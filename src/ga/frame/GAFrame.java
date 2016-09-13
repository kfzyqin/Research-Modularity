package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.dynamicHandler.DynamicHandler;
import ga.operations.postOperators.PostOperator;
import ga.operations.priorOperators.PriorOperator;

/**
 * This class provides a general framework for the genetic algorithm to run.
 * The abstract method 'evolve' is the content for one iteration of GA.
 *
 * @author Siu Kei Muk (David)
 * @since 29/08/16.
 */
public abstract class GAFrame<T extends Chromosome> {

    protected final GAState<T> state;
    protected Statistics<T> statistics;
    protected PriorOperator<T> priorOperator = null;
    protected PostOperator<T> postOperator;
    protected DynamicHandler<T> handler = null;

    public GAFrame(@NotNull final GAState<T> state,
                   @NotNull final PostOperator<T> postOperator,
                   @NotNull final Statistics<T> statistics) {
        this.state = state;
        this.postOperator = postOperator;
        this.statistics = statistics;
    }

    /**
     * Evolves for one generation
     */
    public abstract void evolve();

    public GAState<T> getState() {
        return state;
    }

    public Statistics<T> getStatistics() {
        return statistics;
    }

    public void setStatistics(final Statistics<T> statistics) {
        this.statistics = statistics;
    }

    public void setPriorOperator(final PriorOperator<T> priorOperator){
        this.priorOperator = priorOperator;
    }

    public void setPostOperator(@NotNull final PostOperator<T> postOperator) {
        this.postOperator = postOperator;
    }

    public void setDynamicHandler(final DynamicHandler<T> handler) {
        this.handler = handler;
    }
}
