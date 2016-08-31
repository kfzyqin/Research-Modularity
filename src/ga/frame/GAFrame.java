package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.Chromosome;
import ga.operations.*;

/**
 * Created by david on 29/08/16.
 */
public class GAFrame<T extends Chromosome> {
    private final GAState<T> state;
    private Statistics<T> statistics;
    private PriorOperator<T> priorOperator = null;

    public GAFrame(@NotNull final GAState<T> state,
                   @NotNull final Statistics<T> statistics) {
        this.state = state;
        this.statistics = statistics;
    }

    public GAFrame(Fitness fitness,
                   Initializer<T> initializer,
                   Recombiner<T> recombiner,
                   Mutator mutator,
                   Selector selector,
                   Statistics<T> statistics) {
        Population<T> population = initializer.initialize();
        state = new GAState<>(population, fitness, mutator, recombiner, selector);
        this.statistics = statistics;
        state.record(statistics);
    }

    public void evolve(){
        if (priorOperator != null)
            state.preOperate(priorOperator);
        state.recombine();
        state.mutate();
        state.nextGeneration();
        state.evaluate();
        statistics.nextGeneration();
        state.record(statistics);
    }

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
}
