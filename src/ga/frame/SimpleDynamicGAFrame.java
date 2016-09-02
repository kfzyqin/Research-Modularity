package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

/**
 * Created by david on 2/09/16.
 */
public class SimpleDynamicGAFrame<T extends Chromosome> extends GAFrame<T> {

    public SimpleDynamicGAFrame(@NotNull final Fitness fitness,
                                @NotNull final Initializer<T> initializer,
                                @NotNull final Recombiner<T> recombiner,
                                @NotNull final Mutator mutator,
                                @NotNull final Selector selector,
                                @NotNull final Statistics<T> statistics) {
        super(fitness, initializer, recombiner, mutator, selector, statistics);
    }

    @Override
    public void evolve() {

    }
}
