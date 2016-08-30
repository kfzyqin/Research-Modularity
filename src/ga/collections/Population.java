package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.Chromosome;
import ga.operations.Fitness;
import ga.operations.Mutator;
import ga.operations.Selector;
import ga.others.Copyable;

import java.util.List;
import java.util.Map;

/**
 * Created by david on 27/08/16.
 */
public interface Population<T extends Chromosome> extends Copyable<Population<T>> {
    void evaluate(@NotNull final Fitness fitness);
    void addChildren(@NotNull final List<T> children);
    void addChild(@NotNull final T child);
    void mutate(@NotNull final Mutator mutator);
    void record(@NotNull final Statistics<T> statistics);
    boolean isReady(); // May be duplicated with nextGeneration()
    boolean nextGeneration();
    List<T> select(@NotNull final Selector selector);
    // Map<String, List<T>> getElites(final int amount);
}
