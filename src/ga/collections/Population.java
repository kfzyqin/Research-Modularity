package ga.collections;

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
    void evaluate(Fitness fitness);
    void addChildren(List<T> children);
    void mutate(Mutator mutator);
    void record(Statistics<T> statistics);
    boolean isReady(); // May be duplicated with nextGeneration()
    boolean nextGeneration();
    List<T> select(Selector selector);
    Map<String, List<T>> getElites(final int amount);
}
