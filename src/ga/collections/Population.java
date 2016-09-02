package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.operations.selectors.Selector;
import ga.others.Copyable;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public interface Population<T extends Chromosome> extends Copyable<Population<T>> {

    default void addChildChromosome(@NotNull final T child) {
        addChild(new Individual<>(child));
    };
    default void addChildrenChromosome(@NotNull final List<T> children) {
        for (T child : children) addChildChromosome(child);
    };
    default void addChildren(@NotNull final List<Individual<T>> children) {
        for (Individual<T> child : children) addChild(child);
    };
    void evaluate(@NotNull final Fitness fitness);
    void addChild(@NotNull final Individual<T> child);
    void setPriorPoolMode(final boolean mode);
    void record(@NotNull final Statistics<T> statistics);
    boolean isReady(); // May be duplicated with nextGeneration()
    boolean nextGeneration();
    List<Individual<T>> getIndividualsView();
    List<Individual<T>> getPriorPoolView();
    List<Individual<T>> getSecondPoolView();
    List<Double> getFitnessValuesView();
    List<T> selectMates(@NotNull final Selector selector);
}
